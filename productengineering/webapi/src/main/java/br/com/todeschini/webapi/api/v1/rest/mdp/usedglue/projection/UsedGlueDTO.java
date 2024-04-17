package br.com.todeschini.webapi.api.v1.rest.mdp.usedglue.projection;

import br.com.todeschini.webapi.api.v1.rest.mdp.glue.projection.GlueDTO;
import br.com.todeschini.webapi.api.v1.rest.mdp.mdpson.projection.MDPSonDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public interface UsedGlueDTO {

    @JsonProperty(value = "id", index = 1)
    Long getId();
    @JsonProperty(value = "glue", index = 2)
    GlueDTO getGlue();
    @JsonProperty(value = "son", index = 3)
    MDPSonDTO getSon();
    @JsonProperty(value = "netQuantity", index = 4)
    Double getNetQuantity();
    @JsonProperty(value = "grossQuantity", index = 5)
    Double getGrossQuantity();
    @JsonProperty(value = "measurementUnit", index = 6)
    String getMeasurementUnit();
}
