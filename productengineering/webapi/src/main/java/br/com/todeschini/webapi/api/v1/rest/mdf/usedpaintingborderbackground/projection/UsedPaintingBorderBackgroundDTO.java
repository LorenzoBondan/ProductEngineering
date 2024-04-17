package br.com.todeschini.webapi.api.v1.rest.mdf.usedpaintingborderbackground.projection;

import br.com.todeschini.webapi.api.v1.rest.mdf.paintingborderbackground.projection.PaintingBorderBackgroundDTO;
import br.com.todeschini.webapi.api.v1.rest.mdf.paintingson.projection.PaintingSonDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public interface UsedPaintingBorderBackgroundDTO {

    @JsonProperty(value = "id", index = 1)
    Long getId();
    @JsonProperty(value = "paintingBorderBackground", index = 2)
    PaintingBorderBackgroundDTO getPaintingBorderBackground();
    @JsonProperty(value = "paintingSon", index = 3)
    PaintingSonDTO getPaintingSon();
    @JsonProperty(value = "netQuantity", index = 4)
    Double getNetQuantity();
    @JsonProperty(value = "grossQuantity", index = 5)
    Double getGrossQuantity();
    @JsonProperty(value = "measurementUnit", index = 6)
    String getMeasurementUnit();
}
