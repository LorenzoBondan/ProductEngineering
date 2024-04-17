package br.com.todeschini.webapi.api.v1.rest.aluminium.usedscrew.projection;

import br.com.todeschini.webapi.api.v1.rest.aluminium.screw.projection.ScrewDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public interface UsedScrewDTO {

    @JsonProperty(value = "id", index = 1)
    Long getId();
    @JsonProperty(value = "screw", index = 2)
    ScrewDTO getScrew();
    //@JsonProperty(value = "son", index = 3)
    //AluminiumSonDTO getSon();
    @JsonProperty(value = "quantity", index = 4)
    Double getQuantity();
    @JsonProperty(value = "measurementUnit", index = 5)
    String getMeasurementUnit();
}
