package photon_types

type CreateTenantSpec struct {
	Name string `json:"name"`
}

type Tenant struct {
	ID              string `json:"id"`
	SelfLink        string `json:"selfLink"`
	Name            string `json:"name"`
	Tags            []interface{} `json:"tags"`
	Kind            string `json:"kind"`
	ResourceTickets []struct {
		ID   string `json:"id"`
		Name string `json:"name"`
	} `json:"resourceTickets"`
	Projects        []struct {
		ID   string `json:"id"`
		Name string `json:"name"`
	} `json:"projects"`
	SecurityGroups  []interface{} `json:"securityGroups"`
}

type ProjectList struct {
	Items            []struct {
		ID             string `json:"id"`
		SelfLink       string `json:"selfLink"`
		Name           string `json:"name"`
		Tags           []interface{} `json:"tags"`
		Kind           string `json:"kind"`
		ResourceTicket struct {
						   TenantTicketID   string `json:"tenantTicketId"`
						   TenantTicketName interface{} `json:"tenantTicketName"`
						   Limits           []struct {
							   Key   string `json:"key"`
							   Value int `json:"value"`
							   Unit  string `json:"unit"`
						   } `json:"limits"`
						   Usage            []struct {
							   Key   string `json:"key"`
							   Value int `json:"value"`
							   Unit  string `json:"unit"`
						   } `json:"usage"`
					   } `json:"resourceTicket"`
		SecurityGroups []interface{} `json:"securityGroups"`
	} `json:"items"`
	NextPageLink     interface{} `json:"nextPageLink"`
	PreviousPageLink interface{} `json:"previousPageLink"`
}

type Project struct {
	ID             string `json:"id"`
	SelfLink       string `json:"selfLink"`
	Name           string `json:"name"`
	Tags           []interface{} `json:"tags"`
	Kind           string `json:"kind"`
	ResourceTicket struct {
					   TenantTicketID   string `json:"tenantTicketId"`
					   TenantTicketName interface{} `json:"tenantTicketName"`
					   Limits           []struct {
						   Key   string `json:"key"`
						   Value int `json:"value"`
						   Unit  string `json:"unit"`
					   } `json:"limits"`
					   Usage            []struct {
						   Key   string `json:"key"`
						   Value int `json:"value"`
						   Unit  string `json:"unit"`
					   } `json:"usage"`
				   } `json:"resourceTicket"`
	SecurityGroups []interface{} `json:"securityGroups"`
}

type Auth struct {
	Enabled  bool `json:"enabled"`
	Port     int `json:"port,omitempty"`
	Endpoint string `json:"endpoint,omitempty"`
}

type attachedDiskCreateSpec struct {
	Flavor     string `json:"flavor"`
	//must be 'ephemeral'
	Kind       string `json:"kind"`
	CapacityGb int `json:"capacityGb,omitempty"`
	Name       string `json:"name"`
	BootDisk   bool `json:"bootDisk"`
}

type localitySpec struct {
	//supported values: 'vm', 'disk'
	Kind string `json:"kind"`
	Id   string `json:"id"`
}

type VmCreateSpec struct {
	Flavor        string `json:"flavor"`
	Environment   map[string]string `json:"enviornment,omitempty"`
	SourceImageId string `json:"sourceImageId"`
	AttachedDisks []attachedDiskCreateSpec `json:"attachedDisks"`
	Affinities    []localitySpec `json:"affinities,omitempty"`
	Name          string    `json:"name"`
	Networks      []string    `json:"networks,omitempty"`
	Tags          []string    `json:"tags,omitempty"`
}

type TenantList struct {
	Items            []struct {
		ID              string `json:"id"`
		SelfLink        string `json:"selfLink"`
		Name            string `json:"name"`
		Tags            []interface{} `json:"tags"`
		Kind            string `json:"kind"`
		ResourceTickets []struct {
			ID   string `json:"id"`
			Name string `json:"name"`
		} `json:"resourceTickets"`
		Projects        []struct {
			ID   string `json:"id"`
			Name string `json:"name"`
		} `json:"projects"`
		SecurityGroups  []interface{} `json:"securityGroups"`
	} `json:"items"`
	NextPageLink     interface{} `json:"nextPageLink"`
	PreviousPageLink interface{} `json:"previousPageLink"`
}

//type CreateTenantResponse struct {
//	ID string `json:"id"`
//	SelfLink string `json:"selfLink"`
//	Entity struct {
//		   Kind string `json:"kind"`
//		   ID string `json:"id"`
//	   } `json:"entity"`
//	State string `json:"state"`
//	Steps []interface{} `json:"steps"`
//	Operation string `json:"operation"`
//	StartedTime int64 `json:"startedTime"`
//	QueuedTime int64 `json:"queuedTime"`
//	EndTime int64 `json:"endTime"`
//}

type ResourceTicketCreateSpec struct{
 Name string `json:"name"`
 Limits []QuotaLineItem `json:"limits"`
}

type ProjectCreateSpec struct {
	ResourceTicket ResourceTicketReservation `json:"resourceTicket"`
	Name           string `json:"name"`
	SecurityGroups []string `json:"securityGroups,omitempty"`
}

type ResourceTicketReservation struct {
	Name   string `json:"name"`
	Limits []QuotaLineItem `json:"limits"`
}

type QuotaLineItem  struct {
	//['GB' or ' MB' or ' KB' or ' B' or ' COUNT']: Item unit,
	Unit  string `json:"unit"`
	Value float64 `json:"value"`
	Key   string `json:"key"`
}

type ResourceTicketList struct {
	Items []struct {
		ID string `json:"id"`
		SelfLink string `json:"selfLink"`
		Name string `json:"name"`
		Tags []interface{} `json:"tags"`
		Kind string `json:"kind"`
		TenantID string `json:"tenantId"`
		Limits []struct {
			Key string `json:"key"`
			Value int `json:"value"`
			Unit string `json:"unit"`
		} `json:"limits"`
		Usage []struct {
			Key string `json:"key"`
			Value int `json:"value"`
			Unit string `json:"unit"`
		} `json:"usage"`
	} `json:"items"`
	NextPageLink interface{} `json:"nextPageLink"`
	PreviousPageLink interface{} `json:"previousPageLink"`
}