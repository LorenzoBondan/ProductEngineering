package br.com.todeschini.oauthservicedomain.role;

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
