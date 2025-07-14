package br.com.todeschini.userservicedomain.role;

import br.com.todeschini.libvalidationhandler.metadata.DomainService;
import br.com.todeschini.libvalidationhandler.pageable.PageableRequest;
import br.com.todeschini.libvalidationhandler.pageable.Paged;
import br.com.todeschini.userservicedomain.role.api.RoleService;
import br.com.todeschini.userservicedomain.role.spi.CrudRole;

@DomainService
public class RoleServiceImpl implements RoleService {

    private final CrudRole crudRole;

    public RoleServiceImpl(CrudRole crudRole) {
        this.crudRole = crudRole;
    }

    @Override
    public Paged<DRole> findAll(PageableRequest request) {
        return crudRole.findAll(request);
    }

    @Override
    public DRole find(Integer id) {
        return crudRole.find(id);
    }

    @Override
    public DRole create(DRole obj) {
        return null;
    }

    @Override
    public DRole update(DRole obj) {
        return null;
    }

    @Override
    public void delete(Integer id) {
    }
}
