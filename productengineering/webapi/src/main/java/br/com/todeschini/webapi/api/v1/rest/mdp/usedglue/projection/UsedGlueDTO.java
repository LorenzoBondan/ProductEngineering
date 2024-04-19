package br.com.todeschini.webapi.api.v1.rest.mdp.usedglue.projection;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface UsedGlueDTO {

    @JsonProperty(value = "id", index = 1)
    Long getId();
    @JsonProperty(value = "glueCode", index = 2)
    Long getGlueCode();
    @JsonProperty(value = "sonCode", index = 3)
    Long getSonCode();
    @JsonProperty(value = "netQuantity", index = 4)
    Double getNetQuantity();
    @JsonProperty(value = "grossQuantity", index = 5)
    Double getGrossQuantity();
    @JsonProperty(value = "measurementUnit", index = 6)
    String getMeasurementUnit();
}
