package osv

import (
	"os/exec"
	"github.com/layer-x/layerx-commons/lxlog"
	"github.com/layer-x/layerx-commons/lxerrors"
	"io/ioutil"
	"strings"
"github.com/Sirupsen/logrus"
)

func WrapJavaApplication(javaWrapperDir, appSourceDir string) (string, string, string, error) {
	copyJarWrapper := exec.Command("cp", "-r", "../../containers/osvcompiler/jar-wrapper/", javaWrapperDir)
	javaWrapperDir += "/jar-wrapper"
	lxlog.LogCommand(copyJarWrapper, true)
	err := copyJarWrapper.Run()
	if err != nil {
		return "", "", "", lxerrors.New("copying java wrapper failed", err)
	}
	appPom := readPom(appSourceDir + "/pom.xml")

	groupId := appPom.ChigroupId.Text
	artifactId := appPom.ChiartifactId.Text
	version := appPom.Chiversion.Text

	wrapperPomBytes, err := ioutil.ReadFile(javaWrapperDir + "/pom.xml")
	if err != nil {
		return "", "", "", lxerrors.New("reading app pom bytes", err)
	}
	wrapperPomContents := strings.Replace(string(wrapperPomBytes), "REPLACE_WITH_GROUPID", groupId, -1)
	wrapperPomContents = strings.Replace(wrapperPomContents, "REPLACE_WITH_ARTIFACTID", artifactId, -1)
	wrapperPomContents = strings.Replace(wrapperPomContents, "REPLACE_WITH_VERSION", version, -1)

	err = ioutil.WriteFile(javaWrapperDir + "/pom.xml", []byte(wrapperPomContents), 0666)
	if err != nil {
		return "", "", "", lxerrors.New("writing pom.xml", err)
	}

	mainClassName, err := appPom.getMainClass()
	if err != nil {
		return "", "", "", lxerrors.New("retreiving main class from app", err)
	}
	lxlog.Infof(logrus.Fields{"pom": appPom, "groupid": appPom.ChigroupId, "artifactId": appPom.ChiartifactId, "version": appPom.Chiversion, "mainClassName": mainClassName}, "parsed app pom.xml, gathered relevant fields")

	wrapperMainContentBytes, err := ioutil.ReadFile(javaWrapperDir + "/src/main/java/com/emc/wrapper/Wrapper.java")
	if err != nil {
		return "", "", "", lxerrors.New("reading java pom bytes", err)
	}
	wrapperMainContents := strings.Replace(string(wrapperMainContentBytes), "REPLACE_WITH_MAIN_CLASS", mainClassName, -1)

	err = ioutil.WriteFile(javaWrapperDir + "/src/main/java/com/emc/wrapper/Wrapper.java", []byte(wrapperMainContents), 0666)
	if err != nil {
		return "", "", "", lxerrors.New("writing Wrapper class around app class", err)
	}

	return artifactId, groupId, version, nil
}