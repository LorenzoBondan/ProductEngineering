package br.com.todeschini.webapi.api.v1.rest.aluminium.usedmolding.projection;

import br.com.todeschini.webapi.api.v1.rest.aluminium.molding.projection.MoldingDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public interface UsedMoldingDTO {

    @JsonProperty(value = "id", index = 1)
    Long getId();
    @JsonProperty(value = "molding", index = 2)
    MoldingDTO getMolding();
    //@JsonProperty(value = "son", index = 3)
    //AluminiumSonDTO getSon();
    @JsonProperty(value = "quantity", index = 4)
    Double getQuantity();
    @JsonProperty(value = "measurementUnit", index = 5)
    String getMeasurementUnit();
}
