package ec2api

import (
	"github.com/Sirupsen/logrus"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/layerx-commons/lxlog"
	"os/exec"
"os"
"io/ioutil"
	"github.com/layer-x/unik/pkg/daemon/osv"
)

func BuildJavaUnikernel(unikernelName, unikernelCompilationDir string) error {
	lxlog.Infof(logrus.Fields{"path": unikernelCompilationDir, "unikernel_name": unikernelName, "language_type": "java"}, "compiling java sources into unikernel binary")

	//create java-wrapper dir
	javaWrapperDir, err := ioutil.TempDir(os.TempDir(), unikernelName+"-java-wrapper-dir")
	if err != nil {
		return lxerrors.New("creating temporary directory "+unikernelName+"-java-wrapper-dir", err)
	}
	//clean up artifacts even if we fail
	defer func() {
		err = os.RemoveAll(javaWrapperDir)
		if err != nil {
			panic(lxerrors.New("cleaning up java-wrapper files", err))
		}
		lxlog.Infof(logrus.Fields{"files": javaWrapperDir}, "cleaned up files")
	}()

	artifactId, groupId, version, err := osv.WrapJavaApplication(javaWrapperDir, unikernelCompilationDir)
	if err != nil {
		return lxerrors.New("generating java wrapper application " + unikernelCompilationDir, err)
	}
	lxlog.Infof(logrus.Fields{"artifactId": artifactId, "groupid": groupId, "version": version}, "generated java wrapper")

	buildUnikernelCommand := exec.Command("docker", "run",
		"--rm",
		"--privileged",
		"-v", unikernelCompilationDir + ":/unikernel",
		"-v", javaWrapperDir+"/jar-wrapper" + ":/jar-wrapper",
		"-e", "GROUP_ID=" + groupId,
		"-e", "ARTIFACT_ID=" + artifactId,
		"-e", "VERSION=" + version,
		"osvcompiler",
	)
	lxlog.Infof(logrus.Fields{"cmd": buildUnikernelCommand.Args}, "running build command")
	lxlog.LogCommand(buildUnikernelCommand, true)
	err = buildUnikernelCommand.Run()
	if err != nil {
		return lxerrors.New("building unikernel failed", err)
	}
	lxlog.Infof(logrus.Fields{"unikernel_name": unikernelName}, "unikernel image created")

	//todo: implement osv stager
//	ec2Client, err := ec2_metada_client.NewEC2Client()
//	if err != nil {
//		return lxerrors.New("could not start ec2 client session", err)
//	}
//
//	stageUnikernelCommand := exec.Command("docker", "run",
//		"--rm",
//		"--privileged",
//		"-v", "/dev:/dev",
//		"-v", unikernelCompilationDir +":/unikernel",
//		"rumpstager", "-mode", "aws", "-a", unikernelName)
//
//	lxlog.LogCommand(stageUnikernelCommand, true)
//	err = stageUnikernelCommand.Run()
//	if err != nil {
//		return lxerrors.New("staging unikernel failed", err)
//	}
//	lxlog.Infof(logrus.Fields{"unikernel_name": unikernelName}, "unikernel staging complete")
//	return nil
	return lxerrors.New("osv stager not implemented yet", nil)
}
