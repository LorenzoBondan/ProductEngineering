package br.com.todeschini.webapi.api.v1.rest.auth.role.projection;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface RoleDTO {

    @JsonProperty(value = "id", index = 1)
    Long getId();
    @JsonProperty(value = "authority", index = 2)
    String getAuthority();
}
