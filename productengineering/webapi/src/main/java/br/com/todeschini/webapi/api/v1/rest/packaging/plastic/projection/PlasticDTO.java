package br.com.todeschini.webapi.api.v1.rest.packaging.plastic.projection;

import br.com.todeschini.webapi.api.v1.rest.publico.color.projection.ColorDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public interface PlasticDTO {

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
    @JsonProperty(value = "color", index = 6)
    ColorDTO getColor();
    @JsonProperty(value = "grammage", index = 7)
    Double getGrammage();
    @JsonProperty(value = "value", index = 8)
    Double getValue();
}
