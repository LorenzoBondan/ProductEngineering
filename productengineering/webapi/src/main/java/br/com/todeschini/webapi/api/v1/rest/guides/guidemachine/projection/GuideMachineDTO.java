package br.com.todeschini.webapi.api.v1.rest.guides.guidemachine.projection;

import br.com.todeschini.webapi.api.v1.rest.guides.guide.projection.GuideDTO;
import br.com.todeschini.webapi.api.v1.rest.guides.machine.projection.MachineDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public interface GuideMachineDTO {

    @JsonProperty(value = "id", index = 1)
    Long getId();
    @JsonProperty(value = "guide", index = 2)
    GuideDTO getGuide();
    @JsonProperty(value = "machine", index = 3)
    MachineDTO getMachine();
    @JsonProperty(value = "machineTime", index = 4)
    Double getMachineTime();
    @JsonProperty(value = "manTime", index = 5)
    Double getManTime();
    @JsonProperty(value = "measurementUnit", index = 6)
    String getMeasurementUnit();
}
