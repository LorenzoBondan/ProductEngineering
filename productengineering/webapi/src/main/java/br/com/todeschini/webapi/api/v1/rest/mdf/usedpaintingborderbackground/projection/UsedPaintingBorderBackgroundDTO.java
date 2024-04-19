package br.com.todeschini.webapi.api.v1.rest.mdf.usedpaintingborderbackground.projection;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface UsedPaintingBorderBackgroundDTO {

    @JsonProperty(value = "id", index = 1)
    Long getId();
    @JsonProperty(value = "paintingBorderBackgroundCode", index = 2)
    Long getPaintingBorderBackgroundCode();
    @JsonProperty(value = "paintingSonCode", index = 3)
    Long getPaintingSonCode();
    @JsonProperty(value = "netQuantity", index = 4)
    Double getNetQuantity();
    @JsonProperty(value = "grossQuantity", index = 5)
    Double getGrossQuantity();
    @JsonProperty(value = "measurementUnit", index = 6)
    String getMeasurementUnit();
}
