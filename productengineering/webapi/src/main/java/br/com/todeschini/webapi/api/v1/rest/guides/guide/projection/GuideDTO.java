package br.com.todeschini.webapi.api.v1.rest.guides.guide.projection;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public interface GuideDTO {

    @JsonProperty(value = "id", index = 1)
    Long getId();
    @JsonProperty(value = "description", index = 2)
    String getDescription();
    @JsonProperty(value = "implementation", index = 3)
    LocalDate getImplementation();
    @JsonProperty(value = "finalDate", index = 4)
    LocalDate getFinalDate();
    @JsonProperty(value = "value", index = 5)
    Double getValue();
}
