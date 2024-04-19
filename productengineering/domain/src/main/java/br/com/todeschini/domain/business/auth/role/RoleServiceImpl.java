package br.com.todeschini.domain.business.auth.role;

import br.com.todeschini.domain.business.auth.role.api.RoleService;
import br.com.todeschini.domain.business.auth.role.spi.CrudRole;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.Optional;

@DomainService
public class RoleServiceImpl implements RoleService {

    private final CrudRole crudRole;

    public RoleServiceImpl(CrudRole crudRole) {
        this.crudRole = crudRole;
    }

    @Override
    public DRole find(Long id) {
        return crudRole.find(id);
    }

    @Override
    public DRole insert(DRole domain) {
        validateDuplicatedResource(domain);
        domain.validate();
        return crudRole.insert(domain);
    }

    @Override
    public DRole update(Long id, DRole domain) {
        validateDuplicatedResource(domain);
        domain.validate();
        return crudRole.update(id, domain);
    }

    @Override
    public void delete(Long id) {
        crudRole.delete(id);
    }

    private void validateDuplicatedResource(DRole domain){
        if(crudRole.findByAuthority(domain.getAuthority())
                .stream()
                .anyMatch(t -> !t.getId().equals(Optional.ofNullable(domain.getId()).orElse(-1L)))){
            throw new DuplicatedResourceException("Verifique o campo papel.");
        }
    }
}
