package br.com.todeschini.oauthservicedomain.user;

import br.com.todeschini.oauthservicedomain.role.DRole;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    private List<DRole> roles = new ArrayList<>();
}
