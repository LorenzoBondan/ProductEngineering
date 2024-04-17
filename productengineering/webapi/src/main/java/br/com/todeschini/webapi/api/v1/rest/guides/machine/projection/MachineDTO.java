package br.com.todeschini.webapi.api.v1.rest.guides.machine.projection;

import br.com.todeschini.webapi.api.v1.rest.guides.machinegroup.projection.MachineGroupDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public interface MachineDTO {

    @JsonProperty(value = "id", index = 1)
    Long getId();
    @JsonProperty(value = "name", index = 2)
    String getName();
    @JsonProperty(value = "formula", index = 3)
    String[] getFormula();
    @JsonProperty(value = "machineGroup", index = 4)
    MachineGroupDTO getMachineGroup();
}
