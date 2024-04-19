package br.com.todeschini.webapi.api.v1.rest.aluminium.useddrawerpull.projection;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface UsedDrawerPullDTO {

    @JsonProperty(value = "id", index = 1)
    Long getId();
    @JsonProperty(value = "drawerPullCode", index = 2)
    Long getDrawerPull();
    @JsonProperty(value = "aluminiumSonCode", index = 3)
    Long getAluminiumSonCode();
    @JsonProperty(value = "quantity", index = 4)
    Double getQuantity();
    @JsonProperty(value = "measurementUnit", index = 5)
    String getMeasurementUnit();
}
