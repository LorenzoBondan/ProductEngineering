package br.com.todeschini.webapi.api.v1.rest.mdp.sheet.projection;

import br.com.todeschini.webapi.api.v1.rest.publico.color.projection.ColorDTO;
import br.com.todeschini.webapi.api.v1.rest.publico.material.projection.MaterialDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public interface SheetDTO {

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
    @JsonProperty(value = "faces", index = 6)
    Integer getFaces();
    @JsonProperty(value = "thickness", index = 7)
    Double getThickness();
    @JsonProperty(value = "color", index = 8)
    ColorDTO getColor();
    @JsonProperty(value = "materialId", index = 9)
    Long getMaterialId();
    @JsonProperty(value = "value", index = 10)
    Double getValue();
}
