package capstan
import (
	"fmt"
	"github.com/layer-x/layerx-commons/lxerrors"
	"io/ioutil"
)

func GenerateCapstanFile(srcDirectory string) error {
	pom, err := parsePom(srcDirectory+"/pom.xml")
	if err != nil {
		return lxerrors.New("could not parse pom file", err)
	}
	jarFile := pom.Chiproject.ChiartifactId.Text+"-"+pom.Chiproject.Chiversion.Text+"-jar-with-dependencies.jar"

	fileContents := fmt.Sprintf(`# Name of the base image.  Capstan will download this automatically from
# Cloudius S3 repository.
#
base: cloudius/osv-openjdk

#
# The command line passed to OSv to start up the application.
#
cmdline: /java.so -jar /program.jar

#
# The command to use to build the application.  In this example, we just use
# "mvn package".
#
build: mvn package

#
# List of files that are included in the generated image.
#
files:
  /program.jar: %s`, jarFile)
	err = ioutil.WriteFile(srcDirectory+"/Capstanfile", []byte(fileContents), 0666)
	if err != nil {
		return lxerrors.New("writing capstanfile", err)
	}
	return nil
}

