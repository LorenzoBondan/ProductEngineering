package br.com.todeschini.webapi.api.v1.rest.mdf.paintingson.projection;

import br.com.todeschini.webapi.api.v1.rest.publico.color.projection.ColorDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public interface PaintingSonDTO {

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
    @JsonProperty(value = "satin", index = 8)
    Boolean getSatin();
    @JsonProperty(value = "highShine", index = 9)
    Boolean getHighShine();
    @JsonProperty(value = "satinGlass", index = 10)
    Boolean getSatinGlass();
    @JsonProperty(value = "special", index = 11)
    Boolean getSpecial();
    @JsonProperty(value = "faces", index = 12)
    Integer getFaces();
    @JsonProperty(value = "suffix", index = 13)
    Integer getSuffix();
    @JsonProperty(value = "fatherCode", index = 14)
    Long getFatherCode();
    @JsonProperty(value = "backCode", index = 15)
    Long getBackCode();
    @JsonProperty(value = "guideId", index = 16)
    Long getGuideId();
    @JsonProperty(value = "implementation", index = 17)
    LocalDate getImplementation();
    @JsonProperty(value = "value", index = 18)
    Double getValue();
}
