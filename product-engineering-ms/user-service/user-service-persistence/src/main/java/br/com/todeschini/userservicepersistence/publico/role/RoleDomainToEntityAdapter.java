package br.com.todeschini.userservicepersistence.publico.role;

import br.com.todeschini.libvalidationhandler.contracts.Convertable;
import br.com.todeschini.userservicedomain.role.DRole;
import br.com.todeschini.userservicepersistence.entities.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleDomainToEntityAdapter implements Convertable<Role, DRole> {

    @Override
    public Role toEntity(DRole domain) {
        return Role.builder()
                .id(domain.getId())
                .authority(domain.getAuthority())
                .build();
    }

    @Override
    public DRole toDomain(Role entity) {
        return DRole.builder()
                .id(entity.getId())
                .authority(entity.getAuthority())
                .build();
    }
}
