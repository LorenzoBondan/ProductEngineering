package br.com.todeschini.libauthservicedomain.user;

import br.com.todeschini.libauthservicedomain.role.DRole;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DUser {

    private Integer id;
    private String name;
    private String email;
    private String password;

    private Set<DRole> roles = new HashSet<>();

    public boolean hasRole(String roleName) {
        for (DRole role : roles) {
            if(role.getAuthority().equals(roleName)) {
                return true;
            }
        }
        return false;
    }
}
