package br.com.todeschini.webapi.api.v1.rest.packaging.polyethylene.projection;

import br.com.todeschini.webapi.api.v1.rest.publico.color.projection.ColorDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public interface PolyethyleneDTO {

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
    @JsonProperty(value = "value", index = 7)
    Double getValue();
    @JsonProperty(value = "status", index = 8)
    String getStatus();
}
