package br.com.todeschini.webapi.api.v1.rest.publico.material.projection;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface MaterialDTO {

    @JsonProperty(value = "id", index = 1)
    Long getId();
    @JsonProperty(value = "name", index = 2)
    String getName();
    @JsonProperty(value = "status", index = 3)
    String getStatus();
}
