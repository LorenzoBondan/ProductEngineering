package br.com.todeschini.webapi.api.v1.rest.mdp.mdpson.projection;

import br.com.todeschini.webapi.api.v1.rest.publico.color.projection.ColorDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public interface MDPSonDTO {

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
    @JsonProperty(value = "color", index = 7)
    ColorDTO getColor();
    @JsonProperty(value = "fatherCode", index = 8)
    Long getFatherCode();
    @JsonProperty(value = "guideId", index = 9)
    Long getGuideId();
    @JsonProperty(value = "implementation", index = 10)
    LocalDate getImplementation();
    @JsonProperty(value = "value", index = 11)
    Double getValue();
    @JsonProperty(value = "status", index = 12)
    String getStatus();
}
