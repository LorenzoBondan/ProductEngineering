package br.com.todeschini.webapi.api.v1.rest.mdf.painting.projection;

import br.com.todeschini.webapi.api.v1.rest.mdf.paintingtype.projection.PaintingTypeDTO;
import br.com.todeschini.webapi.api.v1.rest.publico.color.projection.ColorDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public interface PaintingDTO {

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
    @JsonProperty(value = "paintingType", index = 6)
    PaintingTypeDTO getPaintingType();
    @JsonProperty(value = "color", index = 7)
    ColorDTO getColor();
}
