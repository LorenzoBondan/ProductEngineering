package br.com.todeschini.webapi.api.v1.rest.mdf.paintingtype.projection;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface PaintingTypeDTO {

    @JsonProperty(value = "id", index = 1)
    Long getId();
    @JsonProperty(value = "description", index = 2)
    String getDescription();
    @JsonProperty(value = "status", index = 3)
    String getStatus();
}
