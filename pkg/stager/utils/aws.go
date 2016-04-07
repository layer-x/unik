package utils

import (
	"encoding/xml"
	"io/ioutil"
	"os"
	"path"
	"time"

	"bytes"
	"io"

	"github.com/aws/aws-sdk-go/aws"
	"github.com/aws/aws-sdk-go/service/ec2"
	"github.com/aws/aws-sdk-go/service/s3"
	"github.com/layer-x/unik/containers/rumpstager/model"
	"fmt"
)

func UploadFileToAws(s3svc *s3.S3, file, bucket, path string) error {
	fileInfo, err := os.Stat(file)
	if err != nil {
		return nil
	}

	reader, err := os.Open(file)
	if err != nil {
		return nil
	}
	defer reader.Close()
	return UploadToAws(s3svc, reader, fileInfo.Size(), bucket, path)
}

func UploadToAws(s3svc *s3.S3, body io.ReadSeeker, size int64, bucket, path string) error {
	// upload
	params := &s3.PutObjectInput{
		Bucket:        aws.String(bucket), // required
		Key:           aws.String(path),   // required
		ACL:           aws.String("private"),
		Body:          body,
		ContentLength: aws.Int64(size),
		ContentType:   aws.String("application/octet-stream"),
	}

	_, err := s3svc.PutObject(params)

	if err != nil {
		return err
	}
	return nil
}

func CreateDataVolume(s3svc *s3.S3, ec2svc *ec2.EC2, folder string, az string) error {
	dir, err := ioutil.TempDir("", "")
	if err != nil {
		return err
	}
	defer os.RemoveAll(dir)
	imgFile := path.Join(dir, "vol.img")

	CreateSingleVolume(imgFile, model.Volume{Path: folder})

	fileInfo, err := os.Stat(imgFile)
	if err != nil {
		return err
	}
	// upload the iamge file to aws
	bucket := "fds"

	// TODO: create bucket if needed
	pathInBucket := "yuval"

	err = UploadFileToAws(s3svc, imgFile, bucket, pathInBucket)
	if err != nil {
		return err
	}

	// create signed urls for the file (get, head, delete)
	// s.s3svc.

	getReq, _ := s3svc.GetObjectRequest(&s3.GetObjectInput{
		Bucket: aws.String(bucket),
		Key:    aws.String(pathInBucket),
	})
	getUrlStr, err := getReq.Presign(24 * time.Hour)
	if err != nil {
		return err
	}

	headReq, _ := s3svc.HeadObjectRequest(&s3.HeadObjectInput{
		Bucket: aws.String(bucket),
		Key:    aws.String(pathInBucket),
	})

	headUrlStr, err := headReq.Presign(24 * time.Hour)
	if err != nil {
		return err
	}

	deleteReq, _ := s3svc.DeleteObjectRequest(&s3.DeleteObjectInput{
		Bucket: aws.String(bucket),
		Key:    aws.String(pathInBucket),
	})

	deleteUrlStr, err := deleteReq.Presign(24 * time.Hour)
	if err != nil {
		return err
	}

	// create manifest
	manifestName := "manipani.xml"

	deleteManiReq, _ := s3svc.DeleteObjectRequest(&s3.DeleteObjectInput{
		Bucket: aws.String(bucket),
		Key:    aws.String(manifestName),
	})

	deleteManiUrlStr, err := deleteManiReq.Presign(24 * time.Hour)
	if err != nil {
		return err
	}

	m := Manifest{
		Version:         "2010-11-15",
		FileFormat:      "RAW",
		Importer:        Importer{"unik", "1", "2016-04-01"},
		SelfDestructUrl: deleteManiUrlStr,
		ImportSpec: ImportSpec{
			Size:       fileInfo.Size(),
			VolumeSize: toGigs(fileInfo.Size()),
			Parts: Parts{
				Count: 1,
				Parts: []Part{
					Part{
						Index: 0,
						ByteRange: ByteRange{
							Start: 0,
							End:   fileInfo.Size(),
						},
						Key:       pathInBucket,
						HeadUrl:   headUrlStr,
						GetUrl:    getUrlStr,
						DeleteUrl: deleteUrlStr,
					},
				},
			},
		},
	}
	// convert Manifest to io.ReadSeeker
	buf := new(bytes.Buffer)
	enc := xml.NewEncoder(buf)
	if err := enc.Encode(m); err != nil {
		return err
	}

	// upload manifest
	manifestBytes := buf.Bytes()
	err = UploadToAws(s3svc, bytes.NewReader(manifestBytes), int64(len(manifestBytes)), bucket, manifestName)
	if err != nil {
		return err
	}

	getManiReq, _ := s3svc.GetObjectRequest(&s3.GetObjectInput{
		Bucket: aws.String(bucket),
		Key:    aws.String(manifestName),
	})
	getManiUrlStr, err := getManiReq.Presign(24 * time.Hour)
	if err != nil {
		return err
	}

	// finally import the image

	volparams := &ec2.ImportVolumeInput{
		AvailabilityZone: aws.String(az), // Required
		Image: &ec2.DiskImageDetail{ // Required
			Bytes:             aws.Int64(fileInfo.Size()), // Required
			Format:            aws.String("RAW"),          // Required
			ImportManifestUrl: aws.String(getManiUrlStr),  // Required
		},
		Volume: &ec2.VolumeDetail{ // Required
			Size: aws.Int64(1), // Required
		},
	}
	task, err := ec2svc.ImportVolume(volparams)

	if err != nil {
		return err
	}
	taskInput := &ec2.DescribeConversionTasksInput{
		ConversionTaskIds: []*string{task.ConversionTask.ConversionTaskId},
	}
	err = ec2svc.WaitUntilConversionTaskCompleted(taskInput)

	if err != nil {
		return err
	}
    
    // hopefully successful!
    convTask, err := ec2svc.DescribeConversionTasks(taskInput)
    
	if err != nil {
		return err
	}

    fmt.Printf("%v\n", convTask)    
    
	return nil

}

func toGigs(i int64) int64 {
	return 1 + (i >> 20)
}

type Manifest struct {
	XMLName xml.Name `xml:"manifest"`

	Version         string   `xml:"version"`
	FileFormat      string   `xml:"file-format"`
	Importer        Importer `xml:"importer"`
	SelfDestructUrl string   `xml:"self-destruct-url"`

	ImportSpec ImportSpec `xml:"import"`
}

type Importer struct {
	Name    string `xml:"name"`
	Version string `xml:"version"`
	Release string `xml:"release"`
}

type ImportSpec struct {
	Size       int64 `xml:"size"`
	VolumeSize int64 `xml:"volume-size"`
	Parts      Parts `xml:"parts"`
}
type Parts struct {
	Count int    `xml:"count,attr"`
	Parts []Part `xml:"part"`
}

type Part struct {
	Index     int       `xml:"index,attr"`
	ByteRange ByteRange `xml:"byte-range"`
	Key       string    `xml:"key"`
	HeadUrl   string    `xml:"head-url"`
	GetUrl    string    `xml:"get-url"`
	DeleteUrl string    `xml:"delete-url"`
}
type ByteRange struct {
	Start int64 `xml:"start,attr"`
	End   int64 `xml:"end,attr"`
}
