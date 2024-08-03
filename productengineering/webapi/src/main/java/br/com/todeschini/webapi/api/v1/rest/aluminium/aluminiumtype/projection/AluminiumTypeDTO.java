package br.com.todeschini.webapi.api.v1.rest.aluminium.aluminiumtype.projection;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface AluminiumTypeDTO {

    @JsonProperty(value = "id", index = 1)
    Long getId();
    @JsonProperty(value = "name", index = 2)
    String getName();
    @JsonProperty(value = "lessQuantity", index = 3)
    Double getLessQuantity();
    @JsonProperty(value = "status", index = 4)
    String getStatus();
}
