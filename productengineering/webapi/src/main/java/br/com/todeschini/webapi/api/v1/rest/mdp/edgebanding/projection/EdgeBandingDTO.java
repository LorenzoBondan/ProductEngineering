package br.com.todeschini.webapi.api.v1.rest.mdp.edgebanding.projection;

import br.com.todeschini.webapi.api.v1.rest.publico.color.projection.ColorDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public interface EdgeBandingDTO {

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
    @JsonProperty(value = "height", index = 6)
    Integer getHeight();
    @JsonProperty(value = "thickness", index = 7)
    Integer getThickness();
    @JsonProperty(value = "color", index = 8)
    ColorDTO getColor();
    @JsonProperty(value = "value", index = 9)
    Double getValue();
}
