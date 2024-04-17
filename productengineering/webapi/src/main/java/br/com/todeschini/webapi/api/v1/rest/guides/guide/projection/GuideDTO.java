package br.com.todeschini.webapi.api.v1.rest.guides.guide.projection;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public interface GuideDTO {

    @JsonProperty(value = "id", index = 1)
    Long getId();
    @JsonProperty(value = "implementation", index = 2)
    LocalDate getImplementation();
    @JsonProperty(value = "finalDate", index = 3)
    LocalDate getFinalDate();
}
