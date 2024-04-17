package br.com.todeschini.webapi.api.v1.rest.aluminium.usedglass.projection;

import br.com.todeschini.webapi.api.v1.rest.aluminium.glass.projection.GlassDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public interface UsedGlassDTO {

    @JsonProperty(value = "id", index = 1)
    Long getId();
    @JsonProperty(value = "glass", index = 2)
    GlassDTO getGlass();
    //@JsonProperty(value = "son", index = 3)
    //AluminiumSonDTO getSon();
    @JsonProperty(value = "quantity", index = 4)
    Double getQuantity();
    @JsonProperty(value = "measurementUnit", index = 5)
    String getMeasurementUnit();
}
