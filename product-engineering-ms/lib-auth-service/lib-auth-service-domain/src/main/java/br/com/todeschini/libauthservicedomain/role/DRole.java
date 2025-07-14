package br.com.todeschini.libauthservicedomain.role;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DRole {

    private Integer id;
    private String authority;
}
