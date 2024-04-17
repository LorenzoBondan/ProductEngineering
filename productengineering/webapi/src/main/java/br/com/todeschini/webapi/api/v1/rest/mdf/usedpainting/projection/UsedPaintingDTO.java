package br.com.todeschini.webapi.api.v1.rest.mdf.usedpainting.projection;

import br.com.todeschini.webapi.api.v1.rest.mdf.painting.projection.PaintingDTO;
import br.com.todeschini.webapi.api.v1.rest.mdf.paintingson.projection.PaintingSonDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public interface UsedPaintingDTO {

    @JsonProperty(value = "id", index = 1)
    Long getId();
    @JsonProperty(value = "painting", index = 2)
    PaintingDTO getPainting();
    @JsonProperty(value = "paintingSon", index = 3)
    PaintingSonDTO getPaintingSon();
    @JsonProperty(value = "netQuantity", index = 4)
    Double getNetQuantity();
    @JsonProperty(value = "grossQuantity", index = 5)
    Double getGrossQuantity();
    @JsonProperty(value = "measurementUnit", index = 6)
    String getMeasurementUnit();
}
