package br.com.todeschini.webapi.api.v1.rest.packaging.usedcornerbracket.projection;

import br.com.todeschini.webapi.api.v1.rest.packaging.cornerbracket.projection.CornerBracketDTO;
import br.com.todeschini.webapi.api.v1.rest.packaging.ghost.projection.GhostDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public interface UsedCornerBracketDTO {

    @JsonProperty(value = "id", index = 1)
    Long getId();
    @JsonProperty(value = "cornerBracket", index = 2)
    CornerBracketDTO getCornerBracket();
    @JsonProperty(value = "ghost", index = 3)
    GhostDTO getGhost();
    @JsonProperty(value = "netQuantity", index = 4)
    Double getNetQuantity();
    @JsonProperty(value = "grossQuantity", index = 5)
    Double getGrossQuantity();
    @JsonProperty(value = "measurementUnit", index = 6)
    String getMeasurementUnit();
}
