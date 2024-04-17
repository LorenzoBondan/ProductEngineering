package br.com.todeschini.webapi.api.v1.rest.packaging.usednonwovenfabric.projection;

import br.com.todeschini.webapi.api.v1.rest.packaging.ghost.projection.GhostDTO;
import br.com.todeschini.webapi.api.v1.rest.packaging.nonwovenfabric.projection.NonwovenFabricDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public interface UsedNonwovenFabricDTO {

    @JsonProperty(value = "id", index = 1)
    Long getId();
    @JsonProperty(value = "nonwovenFabric", index = 2)
    NonwovenFabricDTO getNonwovenFabric();
    @JsonProperty(value = "ghost", index = 3)
    GhostDTO getGhost();
    @JsonProperty(value = "netQuantity", index = 4)
    Double getNetQuantity();
    @JsonProperty(value = "grossQuantity", index = 5)
    Double getGrossQuantity();
    @JsonProperty(value = "measurementUnit", index = 6)
    String getMeasurementUnit();
}
