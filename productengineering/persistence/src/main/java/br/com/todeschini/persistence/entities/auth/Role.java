package br.com.todeschini.persistence.entities.auth;

import javax.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_role")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String authority;
}
