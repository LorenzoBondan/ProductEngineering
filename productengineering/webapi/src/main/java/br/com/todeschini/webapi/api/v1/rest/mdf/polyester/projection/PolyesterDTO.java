package br.com.todeschini.webapi.api.v1.rest.mdf.polyester.projection;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public interface PolyesterDTO {

    @JsonProperty(value = "code", index = 1)
    Long getCode();
    @JsonProperty(value = "description", index = 2)
    String getDescription();
    @JsonProperty(value = "family", index = 3)
    String getFamily();
    @JsonProperty(value = "implementation", index = 4)
    LocalDate getImplementation();
    @JsonProperty(value = "lostPercentage", index = 5)
    Double getLostPercentage();
    @JsonProperty(value = "value", index = 6)
    Double getValue();
    @JsonProperty(value = "status", index = 7)
    String getStatus();
}
