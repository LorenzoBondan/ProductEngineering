package br.com.todeschini.webapi.api.v1.rest.guides.machinegroup.projection;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface MachineGroupDTO {

    @JsonProperty(value = "id", index = 1)
    Long getId();
    @JsonProperty(value = "name", index = 2)
    String getName();
}
