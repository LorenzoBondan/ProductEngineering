package br.com.todeschini.webapi.api.v1.rest.mdf.usedpolyester.projection;

import br.com.todeschini.webapi.api.v1.rest.mdf.polyester.projection.PolyesterDTO;
import br.com.todeschini.webapi.api.v1.rest.mdp.mdpson.projection.MDPSonDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public interface UsedPolyesterDTO {

    @JsonProperty(value = "id", index = 1)
    Long getId();
    @JsonProperty(value = "polyester", index = 2)
    PolyesterDTO getPolyester();
    @JsonProperty(value = "son", index = 3)
    MDPSonDTO getSon();
    @JsonProperty(value = "netQuantity", index = 4)
    Double getNetQuantity();
    @JsonProperty(value = "grossQuantity", index = 5)
    Double getGrossQuantity();
    @JsonProperty(value = "measurementUnit", index = 6)
    String getMeasurementUnit();
}
