package br.com.todeschini.domain.business.auth.role;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DRole {

    @EqualsAndHashCode.Include
    private Long id;
    private String authority;
}
