package br.com.todeschini.webapi.api.v1.rest.packaging.ghost.projection;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface GhostDTO {

    @JsonProperty(value = "code", index = 1)
    String getCode();
    @JsonProperty(value = "suffix", index = 2)
    String getSuffix();
    @JsonProperty(value = "description", index = 3)
    String getDescription();
    @JsonProperty(value = "measure1", index = 4)
    Integer getMeasure1();
    @JsonProperty(value = "measure2", index = 5)
    Integer getMeasure2();
    @JsonProperty(value = "measure3", index = 6)
    Integer getMeasure3();
    @JsonProperty(value = "value", index = 7)
    Double getValue();
    @JsonProperty(value = "status", index = 8)
    String getStatus();
}
