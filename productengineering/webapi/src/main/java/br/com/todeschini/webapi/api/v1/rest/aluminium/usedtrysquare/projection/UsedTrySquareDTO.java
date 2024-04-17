package br.com.todeschini.webapi.api.v1.rest.aluminium.usedtrysquare.projection;

import br.com.todeschini.webapi.api.v1.rest.aluminium.trysquare.projection.TrySquareDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public interface UsedTrySquareDTO {

    @JsonProperty(value = "id", index = 1)
    Long getId();
    @JsonProperty(value = "trySquare", index = 2)
    TrySquareDTO getTrySquare();
    //@JsonProperty(value = "son", index = 3)
    //AluminiumSonDTO getSon();
    @JsonProperty(value = "quantity", index = 4)
    Double getQuantity();
    @JsonProperty(value = "measurementUnit", index = 5)
    String getMeasurementUnit();
}
