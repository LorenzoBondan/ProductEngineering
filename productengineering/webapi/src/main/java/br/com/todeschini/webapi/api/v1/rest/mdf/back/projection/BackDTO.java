package br.com.todeschini.webapi.api.v1.rest.mdf.back.projection;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public interface BackDTO {

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
    @JsonProperty(value = "thickness", index = 6)
    Double getThickness();
    @JsonProperty(value = "suffix", index = 7)
    Integer getSuffix();
    @JsonProperty(value = "measure1", index = 8)
    Integer getMeasure1();
    @JsonProperty(value = "measure2", index = 9)
    Integer getMeasure2();
    @JsonProperty(value = "value", index = 10)
    Double getValue();
    @JsonProperty(value = "status", index = 11)
    String getStatus();
}
