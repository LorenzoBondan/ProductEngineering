package br.com.todeschini.webapi.api.v1.rest.auth.user.projection;

import br.com.todeschini.webapi.api.v1.rest.auth.role.projection.RoleDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public interface UserDTO {

    @JsonProperty(value = "id", index = 1)
    Long getId();
    @JsonProperty(value = "name", index = 2)
    String getName();
    @JsonProperty(value = "email", index = 3)
    String getEmail();
    @JsonProperty(value = "imgUrl", index = 4)
    String getImgUrl();
    @JsonProperty(value = "roles", index = 5)
    List<RoleDTO> getRoles();
}
