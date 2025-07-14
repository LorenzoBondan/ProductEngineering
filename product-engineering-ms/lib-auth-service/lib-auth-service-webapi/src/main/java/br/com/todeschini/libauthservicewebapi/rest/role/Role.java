package br.com.todeschini.libauthservicewebapi.rest.role;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    private Integer id;
    private String authority;
}
