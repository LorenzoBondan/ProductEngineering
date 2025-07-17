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
    public Paged<DRole> buscar(PageableRequest request) {
        return crudRole.buscar(request);
    }

    @Override
    public DRole buscar(Integer id) {
        return crudRole.buscar(id);
    }

    @Override
    public DRole incluir(DRole obj) {
        return null;
    }

    @Override
    public DRole atualizar(DRole obj) {
        return null;
    }

    @Override
    public void inativar(Integer id) {

    }

    @Override
    public void excluir(Integer id) {
    }
}
