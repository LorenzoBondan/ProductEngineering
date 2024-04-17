package br.com.todeschini.webapi.api.v1.rest.mdp.usededgebanding.projection;

import br.com.todeschini.webapi.api.v1.rest.mdp.edgebanding.projection.EdgeBandingDTO;
import br.com.todeschini.webapi.api.v1.rest.mdp.mdpson.projection.MDPSonDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public interface UsedEdgeBandingDTO {

    @JsonProperty(value = "id", index = 1)
    Long getId();
    @JsonProperty(value = "edgeBanding", index = 2)
    EdgeBandingDTO getEdgeBanding();
    @JsonProperty(value = "son", index = 3)
    MDPSonDTO getSon();
    @JsonProperty(value = "netQuantity", index = 4)
    Double getNetQuantity();
    @JsonProperty(value = "grossQuantity", index = 5)
    Double getGrossQuantity();
    @JsonProperty(value = "measurementUnit", index = 6)
    String getMeasurementUnit();
}
