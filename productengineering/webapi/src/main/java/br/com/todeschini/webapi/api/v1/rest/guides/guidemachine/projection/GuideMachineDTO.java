package br.com.todeschini.webapi.api.v1.rest.guides.guidemachine.projection;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface GuideMachineDTO {

    @JsonProperty(value = "id", index = 1)
    Long getId();
    @JsonProperty(value = "guideId", index = 2)
    Long getGuideId();
    @JsonProperty(value = "machineId", index = 3)
    Long getMachineId();
    @JsonProperty(value = "machineTime", index = 4)
    Double getMachineTime();
    @JsonProperty(value = "manTime", index = 5)
    Double getManTime();
    @JsonProperty(value = "measurementUnit", index = 6)
    String getMeasurementUnit();
}
