package br.com.todeschini.webapi.api.v1.rest.aluminium.usedmolding.projection;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface UsedMoldingDTO {

    @JsonProperty(value = "id", index = 1)
    Long getId();
    @JsonProperty(value = "moldingCode", index = 2)
    Long getMoldingCode();
    @JsonProperty(value = "aluminiumSonCode", index = 3)
    Long getAluminiumSonCode();
    @JsonProperty(value = "quantity", index = 4)
    Double getQuantity();
    @JsonProperty(value = "measurementUnit", index = 5)
    String getMeasurementUnit();
}
