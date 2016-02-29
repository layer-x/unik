package client
import (
	"github.com/layer-x/unik/cmd/daemon/photon/photon_types"
	"github.com/layer-x/layerx-commons/lxhttpclient"
	"github.com/layer-x/layerx-commons/lxerrors"
	"fmt"
)

type PhotonClient struct {
	url string
	project string
	tenant string
	defaultHeaders map[string]string
}

const (
	auth = "/auth"
	tenants = "/tenants"
	projects = "/projects"
	resource_tickets = "/resource_tickets"

	unik_tenant_name = "unik"
	unik_project_name = "unik"
	unik_resource_ticket_name = "unik"

	 defaultLimits = []photon_types.QuotaLineItem{
		photon_types.QuotaLineItem{
			Key: "vm.memory",
			Unit: "GB",
			Value: 100,
		},
		photon_types.QuotaLineItem{
			Key: "vm",
			Unit: "COUNT",
			Value: 1000,
		},
	},
)

func NewPhotonClient(url string) (*PhotonClient, error) {
	client := &PhotonClient{
		url: url,
		project: unik_project_name,
		tenant: unik_tenant_name,
		defaultHeaders: map[string]string{
			"Content-Type:": "application/json",
		},
	}
	err := client.bootstrapPhoton()
	if err != nil {
		return nil, lxerrors.New("error bootstrapping unik project in photon-controller", err)
	}
	return client, nil
}

func (client *PhotonClient) bootstrapPhoton() error {
	tenant, err := client.GetUnikTenant()
	if err != nil {
		err = client.createUnikTenant()
		if err != nil {
			return lxerrors.New("creating 'unik' tenant", err)
		}
		tenant, err = client.GetUnikTenant()
		if err != nil {
			return lxerrors.New("could not retrieve unik tenant after creation", err)
		}
	}
	_, err = client.GetUnikProject(tenant.ID)
	if err != nil {
		err = client.createUnikProject(tenant.ID)
		if err != nil {
			return lxerrors.New("creating 'unik' project", err)
		}
		_, err = client.GetUnikProject(tenant.ID)
		if err != nil {
			return lxerrors.New("could not retrieve unik project after creation", err)
		}
	}
	return nil
}

func (client *PhotonClient) GetUnikTenant() (*photon_types.Tenant, error) {
	var tenantList *photon_types.TenantList
	resp, body, err := lxhttpclient.GetWithUnmarshal(client.url, tenants+"?name="+unik_tenant_name, client.defaultHeaders, tenantList)
	if err != nil {
		return nil, lxerrors.New("performing GET Tenants request on photon-controller", err)
	}
	if resp.StatusCode != 200 {
		return nil, lxerrors.New(fmt.Printf("performing GET Tenants request on photon-controller; resp was %s, expected 200", string(body)), nil)
	}
	for _, tenantItem := range tenantList.Items {
		if tenantItem.Name == unik_tenant_name {
			var tenant *photon_types.Tenant
			resp, body, err := lxhttpclient.GetWithUnmarshal(client.url, tenants+"/"+tenantItem.ID, client.defaultHeaders, tenant)
			if err != nil {
				return nil, lxerrors.New("performing GET Tenant request on photon-controller", err)
			}
			if resp.StatusCode != 200 {
				return nil, lxerrors.New(fmt.Printf("performing GET Tenant request on photon-controller; resp was %s, expected 200", string(body)), nil)
			}
			return tenant, nil
		}
	}
	return nil, lxerrors.New("unik tenant not found", nil)
}

func (client *PhotonClient) GetUnikProject(unikTenantId string) (*photon_types.Project, error) {
	var projectList *photon_types.ProjectList
	resp, body, err := lxhttpclient.GetWithUnmarshal(client.url, tenants+"/"+ unikTenantId +projects+"?name=unik", client.defaultHeaders, projectList)
	if err != nil {
		return nil, lxerrors.New("performing GET Projects request on photon-controller", err)
	}
	if resp.StatusCode != 200 {
		return nil, lxerrors.New(fmt.Printf("performing GET Projects request on photon-controller; resp was %s, expected 200", string(body)), nil)
	}
	for _, projectItem := range projectList.Items {
		if projectItem.Name == unik_tenant_name {
			var project *photon_types.Project
			resp, body, err := lxhttpclient.GetWithUnmarshal(client.url, projects+"/"+ projectItem.ID, client.defaultHeaders, project)
			if err != nil {
				return nil, lxerrors.New("performing GET Project request on photon-controller", err)
			}
			if resp.StatusCode != 200 {
				return nil, lxerrors.New(fmt.Printf("performing GET Project request on photon-controller; resp was %s, expected 200", string(body)), nil)
			}
			return project, nil
		}
	}
	return nil, lxerrors.New("unik project not found", nil)
}

func (client *PhotonClient) GetUnikResourceTicket(unikTenantId string) (*photon_types.Project, error) {
	var resourceTicketList *photon_types.ResourceTicketList
	resp, body, err := lxhttpclient.GetWithUnmarshal(client.url, tenants+"/"+ unikTenantId +resource_tickets+"?name=unik", client.defaultHeaders, resourceTicketList)
	if err != nil {
		return nil, lxerrors.New("performing GET Resource Tickets request on photon-controller", err)
	}
	if resp.StatusCode != 200 {
		return nil, lxerrors.New(fmt.Printf("performing GET Resource Tickets request on photon-controller; resp was %s, expected 200", string(body)), nil)
	}
	for _, resourceTicket := range resourceTicketList.Items {
		if resourceTicket.Name == unik_tenant_name {
			return resourceTicket, nil
		}
	}
	return nil, lxerrors.New("unik resource ticket not found", nil)
}

func (client *PhotonClient) createUnikTenant() error {
	createTenantSpec := photon_types.CreateTenantSpec{
		Name: unik_tenant_name,
	}
	resp, body, err := lxhttpclient.Post(client.url, tenants, client.defaultHeaders, createTenantSpec)
	if err != nil {
		return lxerrors.New("performing POST create tenant request on photon-controller", err)
	}
	if resp.StatusCode != 201 {
		return lxerrors.New(fmt.Printf("performing POST create tenant request on photon-controller; resp was %s, expected 201", string(body)), nil)
	}
	return nil
}

func (client *PhotonClient) createUnikProject(unikTenantId string) error {
	createProjectSpec := photon_types.ProjectCreateSpec{
		Name: unik_tenant_name,
		ResourceTicket: photon_types.ResourceTicketReservation{
			Name: unik_resource_ticket_name,
			Limits: defaultLimits,
		},
	}
	resp, body, err := lxhttpclient.Post(client.url, tenants+"/"+unikTenantId+projects, client.defaultHeaders, createProjectSpec)
	if err != nil {
		return lxerrors.New("performing POST create tenant request on photon-controller", err)
	}
	if resp.StatusCode != 201 {
		return lxerrors.New(fmt.Printf("performing POST create tenant request on photon-controller; resp was %s, expected 201", string(body)), nil)
	}
	return nil
}

func (client *PhotonClient) createUnikResourceTicket(unikTenantId string) error {
	createResourceTicketSpec := photon_types.ResourceTicketCreateSpec{
		Name: unik_tenant_name,
		Limits: defaultLimits,
	}
	resp, body, err := lxhttpclient.Post(client.url, tenants+"/"+unikTenantId+resource_tickets, client.defaultHeaders, createResourceTicketSpec)
	if err != nil {
		return lxerrors.New("performing POST create resource ticket request on photon-controller", err)
	}
	if resp.StatusCode != 201 {
		return lxerrors.New(fmt.Printf("performing POST create resource ticket request on photon-controller; resp was %s, expected 201", string(body)), nil)
	}
	return nil
}

func (client *PhotonClient) Auth() (*photon_types.Auth, error) {
	var authObject *photon_types.Auth
	resp, body, err := lxhttpclient.GetWithUnmarshal(client.url, auth, client.defaultHeaders, authObject)
	if err != nil {
		return nil, lxerrors.New("performing GET AUTH request on photon-controller", err)
	}
	if resp.StatusCode != 200 {
		return nil, lxerrors.New(fmt.Printf("performing GET AUTH request on photon-controller; resp was %s, expected 200", string(body)), nil)
	}
	return authObject, nil
}

