package br.com.todeschini.webapi.api.v1.rest.guides.machine.projection;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface MachineDTO {

    @JsonProperty(value = "id", index = 1)
    Long getId();
    @JsonProperty(value = "name", index = 2)
    String getName();
    @JsonProperty(value = "formula", index = 3)
    String[] getFormula();
    @JsonProperty(value = "machineGroupId", index = 4)
    Long getMachineGroupId();
    @JsonProperty(value = "value", index = 5)
    Double getValue();
    @JsonProperty(value = "status", index = 6)
    String getStatus();
}
