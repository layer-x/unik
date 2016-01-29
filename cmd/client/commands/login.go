package commands
import "strings"

func Login(name, password, url string) error {
	url = "http://" + strings.TrimPrefix(url, "http://") + ":3000"

}