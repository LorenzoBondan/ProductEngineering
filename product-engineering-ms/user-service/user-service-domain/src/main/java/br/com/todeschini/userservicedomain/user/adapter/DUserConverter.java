package br.com.todeschini.userservicedomain.user.adapter;

import br.com.todeschini.userservicedomain.role.DRole;
import br.com.todeschini.userservicedomain.user.DUser;

import java.util.List;

public class DUserConverter {

    public static DUser convertToDUser(br.com.todeschini.libauthservicedomain.user.DUser domain){
        List<DRole> roles = domain.getRoles().stream()
                .map(role -> DRole.builder()
                        .id(role.getId())
                        .authority(role.getAuthority())
                        .build())
                .toList();

        return DUser.builder()
                .id(domain.getId())
                .name(domain.getName())
                .email(domain.getEmail())
                .password(domain.getPassword())
                .roles(roles)
                .build();
    }
}
