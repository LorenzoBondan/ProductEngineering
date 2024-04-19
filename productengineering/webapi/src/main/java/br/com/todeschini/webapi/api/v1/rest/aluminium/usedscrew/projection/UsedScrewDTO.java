package br.com.todeschini.webapi.api.v1.rest.aluminium.usedscrew.projection;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface UsedScrewDTO {

    @JsonProperty(value = "id", index = 1)
    Long getId();
    @JsonProperty(value = "screwCode", index = 2)
    Long getScrewCode();
    @JsonProperty(value = "aluminiumSonCode", index = 3)
    Long getAluminiumSonCode();
    @JsonProperty(value = "quantity", index = 4)
    Double getQuantity();
    @JsonProperty(value = "measurementUnit", index = 5)
    String getMeasurementUnit();
}
