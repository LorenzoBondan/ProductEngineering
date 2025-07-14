package br.com.todeschini.userservicepersistence.publico.user;

import br.com.todeschini.libvalidationhandler.contracts.Convertable;
import br.com.todeschini.userservicedomain.role.DRole;
import br.com.todeschini.userservicedomain.user.DUser;
import br.com.todeschini.userservicepersistence.entities.Role;
import br.com.todeschini.userservicepersistence.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserDomainToEntityAdapter implements Convertable<User, DUser> {

    @Override
    public User toEntity(DUser domain) {
        return User.builder()
                .id(domain.getId())
                .name(domain.getName())
                .password(domain.getPassword())
                .email(domain.getEmail())

                .roles(domain.getRoles().stream().map(role -> new Role(role.getId(), role.getAuthority())).collect(Collectors.toSet()))

                .build();
    }

    @Override
    public DUser toDomain(User entity) {

        return DUser.builder()
                .id(entity.getId())
                .name(entity.getName())
                .password(entity.getPassword())
                .email(entity.getEmail())

                .roles(entity.getRoles().stream().map(role -> new DRole(role.getId(), role.getAuthority())).collect(Collectors.toList()))

                .build();
    }
}
