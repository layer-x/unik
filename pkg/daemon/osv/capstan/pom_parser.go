package capstan
import (
	"encoding/xml"
	"io/ioutil"
	"github.com/layer-x/layerx-commons/lxerrors"
)

func parsePom(pomPath string) (Chiroot, error) {
	var chiroot Chiroot
	data, err := ioutil.ReadFile(pomPath)
	if err != nil {
		return chiroot, lxerrors.New("could not read file "+pomPath, err)
	}
	err = xml.Unmarshal(data, &chiroot)
	if err != nil {
		return chiroot, lxerrors.New("unmarshal data "+string(data)+" to pom root", err)
	}
	return chiroot, nil
}

type Chiroot struct {
	Chiproject *Chiproject `xml:"http://maven.apache.org/POM/4.0.0 project,omitempty" json:"project,omitempty"`
}

type Chiproject struct {
	Attr_xmlns string `xml:" xmlns,attr"  json:",omitempty"`
	Attr_xsi string `xml:"xmlns xsi,attr"  json:",omitempty"`
	Attr_xsi_schemaLocation string `xml:"http://www.w3.org/2001/XMLSchema-instance schemaLocation,attr"  json:",omitempty"`
	ChiartifactId *ChiartifactId `xml:"http://maven.apache.org/POM/4.0.0 artifactId,omitempty" json:"artifactId,omitempty"`
	Chibuild *Chibuild `xml:"http://maven.apache.org/POM/4.0.0 build,omitempty" json:"build,omitempty"`
	Chidependencies *Chidependencies `xml:"http://maven.apache.org/POM/4.0.0 dependencies,omitempty" json:"dependencies,omitempty"`
	ChigroupId *ChigroupId `xml:"http://maven.apache.org/POM/4.0.0 groupId,omitempty" json:"groupId,omitempty"`
	ChimodelVersion *ChimodelVersion `xml:"http://maven.apache.org/POM/4.0.0 modelVersion,omitempty" json:"modelVersion,omitempty"`
	Chiname *Chiname `xml:"http://maven.apache.org/POM/4.0.0 name,omitempty" json:"name,omitempty"`
	Chipackaging *Chipackaging `xml:"http://maven.apache.org/POM/4.0.0 packaging,omitempty" json:"packaging,omitempty"`
	Chiurl *Chiurl `xml:"http://maven.apache.org/POM/4.0.0 url,omitempty" json:"url,omitempty"`
	Chiversion *Chiversion `xml:"http://maven.apache.org/POM/4.0.0 version,omitempty" json:"version,omitempty"`
	XMLName  xml.Name `xml:"http://maven.apache.org/POM/4.0.0 project,omitempty" json:"project,omitempty"`
}

type ChimodelVersion struct {
	Text string `xml:",chardata" json:",omitempty"`
	XMLName  xml.Name `xml:"http://maven.apache.org/POM/4.0.0 modelVersion,omitempty" json:"modelVersion,omitempty"`
}

type Chipackaging struct {
	Text string `xml:",chardata" json:",omitempty"`
	XMLName  xml.Name `xml:"http://maven.apache.org/POM/4.0.0 packaging,omitempty" json:"packaging,omitempty"`
}

type Chiname struct {
	Text string `xml:",chardata" json:",omitempty"`
	XMLName  xml.Name `xml:"http://maven.apache.org/POM/4.0.0 name,omitempty" json:"name,omitempty"`
}

type Chidependencies struct {
	Chidependency *Chidependency `xml:"http://maven.apache.org/POM/4.0.0 dependency,omitempty" json:"dependency,omitempty"`
	XMLName  xml.Name `xml:"http://maven.apache.org/POM/4.0.0 dependencies,omitempty" json:"dependencies,omitempty"`
}

type Chidependency struct {
	ChiartifactId *ChiartifactId `xml:"http://maven.apache.org/POM/4.0.0 artifactId,omitempty" json:"artifactId,omitempty"`
	ChigroupId *ChigroupId `xml:"http://maven.apache.org/POM/4.0.0 groupId,omitempty" json:"groupId,omitempty"`
	Chiscope *Chiscope `xml:"http://maven.apache.org/POM/4.0.0 scope,omitempty" json:"scope,omitempty"`
	Chiversion *Chiversion `xml:"http://maven.apache.org/POM/4.0.0 version,omitempty" json:"version,omitempty"`
	XMLName  xml.Name `xml:"http://maven.apache.org/POM/4.0.0 dependency,omitempty" json:"dependency,omitempty"`
}

type ChiartifactId struct {
	Text string `xml:",chardata" json:",omitempty"`
	XMLName  xml.Name `xml:"http://maven.apache.org/POM/4.0.0 artifactId,omitempty" json:"artifactId,omitempty"`
}

type Chiversion struct {
	Text string `xml:",chardata" json:",omitempty"`
	XMLName  xml.Name `xml:"http://maven.apache.org/POM/4.0.0 version,omitempty" json:"version,omitempty"`
}

type Chiscope struct {
	Text string `xml:",chardata" json:",omitempty"`
	XMLName  xml.Name `xml:"http://maven.apache.org/POM/4.0.0 scope,omitempty" json:"scope,omitempty"`
}

type ChigroupId struct {
	Text string `xml:",chardata" json:",omitempty"`
	XMLName  xml.Name `xml:"http://maven.apache.org/POM/4.0.0 groupId,omitempty" json:"groupId,omitempty"`
}

type Chibuild struct {
	Chiplugins *Chiplugins `xml:"http://maven.apache.org/POM/4.0.0 plugins,omitempty" json:"plugins,omitempty"`
	XMLName  xml.Name `xml:"http://maven.apache.org/POM/4.0.0 build,omitempty" json:"build,omitempty"`
}

type Chiplugins struct {
	Chiplugin []*Chiplugin `xml:"http://maven.apache.org/POM/4.0.0 plugin,omitempty" json:"plugin,omitempty"`
	XMLName  xml.Name `xml:"http://maven.apache.org/POM/4.0.0 plugins,omitempty" json:"plugins,omitempty"`
}

type Chiplugin struct {
	ChiartifactId *ChiartifactId `xml:"http://maven.apache.org/POM/4.0.0 artifactId,omitempty" json:"artifactId,omitempty"`
	Chiconfiguration *Chiconfiguration `xml:"http://maven.apache.org/POM/4.0.0 configuration,omitempty" json:"configuration,omitempty"`
	Chiexecutions *Chiexecutions `xml:"http://maven.apache.org/POM/4.0.0 executions,omitempty" json:"executions,omitempty"`
	Chiversion *Chiversion `xml:"http://maven.apache.org/POM/4.0.0 version,omitempty" json:"version,omitempty"`
	XMLName  xml.Name `xml:"http://maven.apache.org/POM/4.0.0 plugin,omitempty" json:"plugin,omitempty"`
}

type Chiconfiguration struct {
	Chiarchive *Chiarchive `xml:"http://maven.apache.org/POM/4.0.0 archive,omitempty" json:"archive,omitempty"`
	ChidescriptorRefs *ChidescriptorRefs `xml:"http://maven.apache.org/POM/4.0.0 descriptorRefs,omitempty" json:"descriptorRefs,omitempty"`
	XMLName  xml.Name `xml:"http://maven.apache.org/POM/4.0.0 configuration,omitempty" json:"configuration,omitempty"`
}

type Chiarchive struct {
	Chimanifest *Chimanifest `xml:"http://maven.apache.org/POM/4.0.0 manifest,omitempty" json:"manifest,omitempty"`
	XMLName  xml.Name `xml:"http://maven.apache.org/POM/4.0.0 archive,omitempty" json:"archive,omitempty"`
}

type Chimanifest struct {
	ChimainClass *ChimainClass `xml:"http://maven.apache.org/POM/4.0.0 mainClass,omitempty" json:"mainClass,omitempty"`
	XMLName  xml.Name `xml:"http://maven.apache.org/POM/4.0.0 manifest,omitempty" json:"manifest,omitempty"`
}

type ChimainClass struct {
	Text string `xml:",chardata" json:",omitempty"`
	XMLName  xml.Name `xml:"http://maven.apache.org/POM/4.0.0 mainClass,omitempty" json:"mainClass,omitempty"`
}

type ChidescriptorRefs struct {
	ChidescriptorRef *ChidescriptorRef `xml:"http://maven.apache.org/POM/4.0.0 descriptorRef,omitempty" json:"descriptorRef,omitempty"`
	XMLName  xml.Name `xml:"http://maven.apache.org/POM/4.0.0 descriptorRefs,omitempty" json:"descriptorRefs,omitempty"`
}

type ChidescriptorRef struct {
	Text string `xml:",chardata" json:",omitempty"`
	XMLName  xml.Name `xml:"http://maven.apache.org/POM/4.0.0 descriptorRef,omitempty" json:"descriptorRef,omitempty"`
}

type Chiexecutions struct {
	Chiexecution *Chiexecution `xml:"http://maven.apache.org/POM/4.0.0 execution,omitempty" json:"execution,omitempty"`
	XMLName  xml.Name `xml:"http://maven.apache.org/POM/4.0.0 executions,omitempty" json:"executions,omitempty"`
}

type Chiexecution struct {
	Chiconfiguration *Chiconfiguration `xml:"http://maven.apache.org/POM/4.0.0 configuration,omitempty" json:"configuration,omitempty"`
	Chigoals *Chigoals `xml:"http://maven.apache.org/POM/4.0.0 goals,omitempty" json:"goals,omitempty"`
	Chiphase *Chiphase `xml:"http://maven.apache.org/POM/4.0.0 phase,omitempty" json:"phase,omitempty"`
	XMLName  xml.Name `xml:"http://maven.apache.org/POM/4.0.0 execution,omitempty" json:"execution,omitempty"`
}

type Chiphase struct {
	Text string `xml:",chardata" json:",omitempty"`
	XMLName  xml.Name `xml:"http://maven.apache.org/POM/4.0.0 phase,omitempty" json:"phase,omitempty"`
}

type Chigoals struct {
	Chigoal *Chigoal `xml:"http://maven.apache.org/POM/4.0.0 goal,omitempty" json:"goal,omitempty"`
	XMLName  xml.Name `xml:"http://maven.apache.org/POM/4.0.0 goals,omitempty" json:"goals,omitempty"`
}

type Chigoal struct {
	Text string `xml:",chardata" json:",omitempty"`
	XMLName  xml.Name `xml:"http://maven.apache.org/POM/4.0.0 goal,omitempty" json:"goal,omitempty"`
}

type Chiurl struct {
	Text string `xml:",chardata" json:",omitempty"`
	XMLName  xml.Name `xml:"http://maven.apache.org/POM/4.0.0 url,omitempty" json:"url,omitempty"`
}

