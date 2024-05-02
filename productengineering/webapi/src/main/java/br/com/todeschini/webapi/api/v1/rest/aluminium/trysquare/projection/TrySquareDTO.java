package br.com.todeschini.webapi.api.v1.rest.aluminium.trysquare.projection;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public interface TrySquareDTO {

    @JsonProperty(value = "code", index = 1)
    Long getCode();
    @JsonProperty(value = "description", index = 2)
    String getDescription();
    @JsonProperty(value = "measure1", index = 3)
    Integer getMeasure1();
    @JsonProperty(value = "measure2", index = 4)
    Integer getMeasure2();
    @JsonProperty(value = "measure3", index = 5)
    Integer getMeasure3();
    @JsonProperty(value = "measurementUnit", index = 6)
    String getMeasurementUnit();
    @JsonProperty(value = "implementation", index = 7)
    LocalDate getImplementation();
    @JsonProperty(value = "value", index = 8)
    Double getValue();
}
