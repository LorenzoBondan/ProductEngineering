package br.com.todeschini.webapi.api.v1.rest.publico.color.projection;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface ColorDTO {

    @JsonProperty(value = "code", index = 1)
    Long getCode();
    @JsonProperty(value = "name", index = 2)
    String getName();
}
