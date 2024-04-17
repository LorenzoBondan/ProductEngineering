package br.com.todeschini.webapi.api.v1.rest.mdf.paintingborderbackground.projection;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public interface PaintingBorderBackgroundDTO {

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
}
