package osv

import (
	"bufio"
	"compress/bzip2"
	"compress/gzip"
	"encoding/xml"
	"io"
	"log"
	"os"
	"strings"
	"errors"
)


func readPom(filename string) *Chiproject {
	reader, xmlFile, err := genericReader(filename)
	if err != nil {
		log.Fatal(err)
	}
	if xmlFile != nil {
		defer xmlFile.Close()
	}

	decoder := xml.NewDecoder(reader)
	for {
		token, _ := decoder.Token()
		if token == nil {
			break
		}
		switch se := token.(type) {
		case xml.StartElement:
			return handleFeed(se, decoder)
		}
	}
	return nil
}

func handleFeed(se xml.StartElement, decoder *xml.Decoder) *Chiproject {
	if se.Name.Local == "project" {
		var item Chiproject
		decoder.DecodeElement(&item, &se)
		return &item
	}
	//	if se.Name.Local == "artifactId" {
	//		var item ChiartifactId
	//		decoder.DecodeElement(&item, &se)
	//		writeXml(item)
	//
	//	}
	//
	//	if se.Name.Local == "version" {
	//		var item Chiversion
	//		decoder.DecodeElement(&item, &se)
	//		writeXml(item)
	//
	//	}
	//
	//	if se.Name.Local == "packaging" {
	//		var item Chipackaging
	//		decoder.DecodeElement(&item, &se)
	//		writeXml(item)
	//
	//	}
	//
	//	if se.Name.Local == "url" {
	//		var item Chiurl
	//		decoder.DecodeElement(&item, &se)
	//		writeXml(item)
	//
	//	}
	//
	//	if se.Name.Local == "dependencies" {
	//		var item Chidependencies
	//		decoder.DecodeElement(&item, &se)
	//		writeXml(item)
	//
	//	}
	//
	//	if se.Name.Local == "build" {
	//		var item Chibuild
	//		decoder.DecodeElement(&item, &se)
	//		writeXml(item)
	//
	//	}
	//
	//	if se.Name.Local == "modelVersion" {
	//		var item ChimodelVersion
	//		decoder.DecodeElement(&item, &se)
	//		writeXml(item)
	//
	//	}
	//
	//	if se.Name.Local == "groupId" {
	//		var item ChigroupId
	//		decoder.DecodeElement(&item, &se)
	//		writeXml(item)
	//	}
	//
	//	if se.Name.Local == "name" {
	//		var item Chiname
	//		decoder.DecodeElement(&item, &se)
	//		writeXml(item)
	//	}
	//
	//	if se.Name.Local == "properties" {
	//		var item Chiproperties
	//		decoder.DecodeElement(&item, &se)
	//		writeXml(item)
	//	}
	panic(se.Name.Local+" was not project!")
}

func genericReader(filename string) (io.Reader, *os.File, error) {
	if filename == "" {
		return bufio.NewReader(os.Stdin), nil, nil
	}
	file, err := os.Open(filename)
	if err != nil {
		return nil, nil, err
	}
	if strings.HasSuffix(filename, "bz2") {
		return bufio.NewReader(bzip2.NewReader(bufio.NewReader(file))), file, err
	}

	if strings.HasSuffix(filename, "gz") {
		reader, err := gzip.NewReader(bufio.NewReader(file))
		if err != nil {
			return nil, nil, err
		}
		return bufio.NewReader(reader), file, err
	}
	return bufio.NewReader(file), file, err
}

type Chiroot struct {
	Chiproject *Chiproject `xml:"http://maven.apache.org/POM/4.0.0 project,omitempty" json:"project,omitempty"`
}

type Chiproject struct {
	Attr_xmlns              string `xml:" xmlns,attr"  json:",omitempty"`
	Attr_xsi                string `xml:"xmlns xsi,attr"  json:",omitempty"`
	Attr_xsi_schemaLocation string `xml:"http://www.w3.org/2001/XMLSchema-instance schemaLocation,attr"  json:",omitempty"`
	ChiartifactId           *ChiartifactId `xml:"http://maven.apache.org/POM/4.0.0 artifactId,omitempty" json:"artifactId,omitempty"`
	Chibuild                *Chibuild `xml:"http://maven.apache.org/POM/4.0.0 build,omitempty" json:"build,omitempty"`
	Chidependencies         *Chidependencies `xml:"http://maven.apache.org/POM/4.0.0 dependencies,omitempty" json:"dependencies,omitempty"`
	ChigroupId              *ChigroupId `xml:"http://maven.apache.org/POM/4.0.0 groupId,omitempty" json:"groupId,omitempty"`
	ChimodelVersion         *ChimodelVersion `xml:"http://maven.apache.org/POM/4.0.0 modelVersion,omitempty" json:"modelVersion,omitempty"`
	Chiname                 *Chiname `xml:"http://maven.apache.org/POM/4.0.0 name,omitempty" json:"name,omitempty"`
	Chipackaging            *Chipackaging `xml:"http://maven.apache.org/POM/4.0.0 packaging,omitempty" json:"packaging,omitempty"`
	Chiproperties           *Chiproperties `xml:"http://maven.apache.org/POM/4.0.0 properties,omitempty" json:"properties,omitempty"`
	Chiurl                  *Chiurl `xml:"http://maven.apache.org/POM/4.0.0 url,omitempty" json:"url,omitempty"`
	Chiversion              *Chiversion `xml:"http://maven.apache.org/POM/4.0.0 version,omitempty" json:"version,omitempty"`
	XMLName                 xml.Name `xml:"http://maven.apache.org/POM/4.0.0 project,omitempty" json:"project,omitempty"`
}

type Chibuild struct {
	Chiplugins *Chiplugins `xml:"http://maven.apache.org/POM/4.0.0 plugins,omitempty" json:"plugins,omitempty"`
	XMLName    xml.Name `xml:"http://maven.apache.org/POM/4.0.0 build,omitempty" json:"build,omitempty"`
}

type Chiplugins struct {
	Chiplugin []*Chiplugin `xml:"http://maven.apache.org/POM/4.0.0 plugin,omitempty" json:"plugin,omitempty"`
	XMLName   xml.Name `xml:"http://maven.apache.org/POM/4.0.0 plugins,omitempty" json:"plugins,omitempty"`
}

type Chiplugin struct {
	ChiartifactId    *ChiartifactId `xml:"http://maven.apache.org/POM/4.0.0 artifactId,omitempty" json:"artifactId,omitempty"`
	Chiconfiguration *Chiconfiguration `xml:"http://maven.apache.org/POM/4.0.0 configuration,omitempty" json:"configuration,omitempty"`
	Chiexecutions    *Chiexecutions `xml:"http://maven.apache.org/POM/4.0.0 executions,omitempty" json:"executions,omitempty"`
	ChigroupId       *ChigroupId `xml:"http://maven.apache.org/POM/4.0.0 groupId,omitempty" json:"groupId,omitempty"`
	Chiversion       *Chiversion `xml:"http://maven.apache.org/POM/4.0.0 version,omitempty" json:"version,omitempty"`
	XMLName          xml.Name `xml:"http://maven.apache.org/POM/4.0.0 plugin,omitempty" json:"plugin,omitempty"`
}

type Chiexecutions struct {
	Chiexecution *Chiexecution `xml:"http://maven.apache.org/POM/4.0.0 execution,omitempty" json:"execution,omitempty"`
	XMLName      xml.Name `xml:"http://maven.apache.org/POM/4.0.0 executions,omitempty" json:"executions,omitempty"`
}

type Chiexecution struct {
	Chigoals *Chigoals `xml:"http://maven.apache.org/POM/4.0.0 goals,omitempty" json:"goals,omitempty"`
	Chiphase *Chiphase `xml:"http://maven.apache.org/POM/4.0.0 phase,omitempty" json:"phase,omitempty"`
	XMLName  xml.Name `xml:"http://maven.apache.org/POM/4.0.0 execution,omitempty" json:"execution,omitempty"`
}

type Chiphase struct {
	Text    string `xml:",chardata" json:",omitempty"`
	XMLName xml.Name `xml:"http://maven.apache.org/POM/4.0.0 phase,omitempty" json:"phase,omitempty"`
}

type Chigoals struct {
	Chigoal *Chigoal `xml:"http://maven.apache.org/POM/4.0.0 goal,omitempty" json:"goal,omitempty"`
	XMLName xml.Name `xml:"http://maven.apache.org/POM/4.0.0 goals,omitempty" json:"goals,omitempty"`
}

type Chigoal struct {
	Text    string `xml:",chardata" json:",omitempty"`
	XMLName xml.Name `xml:"http://maven.apache.org/POM/4.0.0 goal,omitempty" json:"goal,omitempty"`
}

type ChigroupId struct {
	Text    string `xml:",chardata" json:",omitempty"`
	XMLName xml.Name `xml:"http://maven.apache.org/POM/4.0.0 groupId,omitempty" json:"groupId,omitempty"`
}

type ChiartifactId struct {
	Text    string `xml:",chardata" json:",omitempty"`
	XMLName xml.Name `xml:"http://maven.apache.org/POM/4.0.0 artifactId,omitempty" json:"artifactId,omitempty"`
}

type Chiversion struct {
	Text    string `xml:",chardata" json:",omitempty"`
	XMLName xml.Name `xml:"http://maven.apache.org/POM/4.0.0 version,omitempty" json:"version,omitempty"`
}

type Chiconfiguration struct {
	Chiarchive        *Chiarchive `xml:"http://maven.apache.org/POM/4.0.0 archive,omitempty" json:"archive,omitempty"`
	ChidescriptorRefs *ChidescriptorRefs `xml:"http://maven.apache.org/POM/4.0.0 descriptorRefs,omitempty" json:"descriptorRefs,omitempty"`
	XMLName           xml.Name `xml:"http://maven.apache.org/POM/4.0.0 configuration,omitempty" json:"configuration,omitempty"`
}

type ChidescriptorRefs struct {
	ChidescriptorRef *ChidescriptorRef `xml:"http://maven.apache.org/POM/4.0.0 descriptorRef,omitempty" json:"descriptorRef,omitempty"`
	XMLName          xml.Name `xml:"http://maven.apache.org/POM/4.0.0 descriptorRefs,omitempty" json:"descriptorRefs,omitempty"`
}

type ChidescriptorRef struct {
	Text    string `xml:",chardata" json:",omitempty"`
	XMLName xml.Name `xml:"http://maven.apache.org/POM/4.0.0 descriptorRef,omitempty" json:"descriptorRef,omitempty"`
}

type Chiarchive struct {
	Chimanifest *Chimanifest `xml:"http://maven.apache.org/POM/4.0.0 manifest,omitempty" json:"manifest,omitempty"`
	XMLName     xml.Name `xml:"http://maven.apache.org/POM/4.0.0 archive,omitempty" json:"archive,omitempty"`
}

type Chimanifest struct {
	ChimainClass *ChimainClass `xml:"http://maven.apache.org/POM/4.0.0 mainClass,omitempty" json:"mainClass,omitempty"`
	XMLName      xml.Name `xml:"http://maven.apache.org/POM/4.0.0 manifest,omitempty" json:"manifest,omitempty"`
}

type ChimainClass struct {
	Text    string `xml:",chardata" json:",omitempty"`
	XMLName xml.Name `xml:"http://maven.apache.org/POM/4.0.0 mainClass,omitempty" json:"mainClass,omitempty"`
}

type ChimodelVersion struct {
	Text    string `xml:",chardata" json:",omitempty"`
	XMLName xml.Name `xml:"http://maven.apache.org/POM/4.0.0 modelVersion,omitempty" json:"modelVersion,omitempty"`
}

type Chipackaging struct {
	Text    string `xml:",chardata" json:",omitempty"`
	XMLName xml.Name `xml:"http://maven.apache.org/POM/4.0.0 packaging,omitempty" json:"packaging,omitempty"`
}

type Chiurl struct {
	Text    string `xml:",chardata" json:",omitempty"`
	XMLName xml.Name `xml:"http://maven.apache.org/POM/4.0.0 url,omitempty" json:"url,omitempty"`
}

type Chidependencies struct {
	Chidependency []*Chidependency `xml:"http://maven.apache.org/POM/4.0.0 dependency,omitempty" json:"dependency,omitempty"`
	XMLName       xml.Name `xml:"http://maven.apache.org/POM/4.0.0 dependencies,omitempty" json:"dependencies,omitempty"`
}

type Chidependency struct {
	ChiartifactId *ChiartifactId `xml:"http://maven.apache.org/POM/4.0.0 artifactId,omitempty" json:"artifactId,omitempty"`
	ChigroupId    *ChigroupId `xml:"http://maven.apache.org/POM/4.0.0 groupId,omitempty" json:"groupId,omitempty"`
	Chiscope      *Chiscope `xml:"http://maven.apache.org/POM/4.0.0 scope,omitempty" json:"scope,omitempty"`
	Chiversion    *Chiversion `xml:"http://maven.apache.org/POM/4.0.0 version,omitempty" json:"version,omitempty"`
	XMLName       xml.Name `xml:"http://maven.apache.org/POM/4.0.0 dependency,omitempty" json:"dependency,omitempty"`
}

type Chiscope struct {
	Text    string `xml:",chardata" json:",omitempty"`
	XMLName xml.Name `xml:"http://maven.apache.org/POM/4.0.0 scope,omitempty" json:"scope,omitempty"`
}

type Chiname struct {
	Text    string `xml:",chardata" json:",omitempty"`
	XMLName xml.Name `xml:"http://maven.apache.org/POM/4.0.0 name,omitempty" json:"name,omitempty"`
}

type Chiproperties struct {
	Chiproject_dot_build_dot_sourceEncoding *Chiproject_dot_build_dot_sourceEncoding `xml:"http://maven.apache.org/POM/4.0.0 project.build.sourceEncoding,omitempty" json:"project.build.sourceEncoding,omitempty"`
	XMLName                                 xml.Name `xml:"http://maven.apache.org/POM/4.0.0 properties,omitempty" json:"properties,omitempty"`
}

type Chiproject_dot_build_dot_sourceEncoding struct {
	Text    string `xml:",chardata" json:",omitempty"`
	XMLName xml.Name `xml:"http://maven.apache.org/POM/4.0.0 project.build.sourceEncoding,omitempty" json:"project.build.sourceEncoding,omitempty"`
}



func (project *Chiproject) getMainClass() (string, error) {
	if project.Chibuild != nil {
		for _, plugin := range project.Chibuild.Chiplugins.Chiplugin {
			if plugin.Chiconfiguration != nil &&
			plugin.Chiconfiguration != nil &&
			plugin.Chiconfiguration.Chiarchive != nil &&
			plugin.Chiconfiguration.Chiarchive.Chimanifest != nil &&
			plugin.Chiconfiguration.Chiarchive.Chimanifest.ChimainClass != nil {
				return plugin.Chiconfiguration.Chiarchive.Chimanifest.ChimainClass.Text, nil
			}
		}
	}
	return "", errors.New("main class not found")
}
