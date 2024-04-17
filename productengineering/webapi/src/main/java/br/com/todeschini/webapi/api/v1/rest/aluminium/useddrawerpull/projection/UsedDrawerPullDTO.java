package br.com.todeschini.webapi.api.v1.rest.aluminium.useddrawerpull.projection;

import br.com.todeschini.webapi.api.v1.rest.aluminium.drawerpull.projection.DrawerPullDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

public interface UsedDrawerPullDTO {

    @JsonProperty(value = "id", index = 1)
    Long getId();
    @JsonProperty(value = "drawerPull", index = 2)
    DrawerPullDTO getDrawerPull();
    //@JsonProperty(value = "son", index = 3)
    //AluminiumSonDTO getSon();
    @JsonProperty(value = "quantity", index = 4)
    Double getQuantity();
    @JsonProperty(value = "measurementUnit", index = 5)
    String getMeasurementUnit();
}
