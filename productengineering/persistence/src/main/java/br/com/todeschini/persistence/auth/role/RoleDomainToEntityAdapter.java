package br.com.todeschini.persistence.auth.role;

import br.com.todeschini.domain.business.auth.role.DRole;
import br.com.todeschini.persistence.entities.auth.Role;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class RoleDomainToEntityAdapter implements Convertable<Role, DRole> {

    @Override
    public Role toEntity(DRole domain) {
        return Role.builder()
                .id(domain.getId())
                .authority(domain.getAuthority().toUpperCase())
                .build();
    }

    @Override
    public DRole toDomain(Role entity) {
        return DRole.builder()
                .id(entity.getId())
                .authority(entity.getAuthority().toUpperCase())
                .build();
    }
}
