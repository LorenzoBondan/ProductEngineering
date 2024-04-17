package br.com.todeschini.domain.business.packaging.ghost;

import br.com.todeschini.domain.business.packaging.ghost.api.GhostService;
import br.com.todeschini.domain.business.packaging.ghost.spi.CrudGhost;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;

@DomainService
public class GhostServiceImpl implements GhostService {

    private final CrudGhost crudGhost;

    public GhostServiceImpl(CrudGhost crudGhost) {
        this.crudGhost = crudGhost;
    }

    @Override
    public List<DGhost> findAllActiveAndCurrentOne(String id) {
        return crudGhost.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DGhost find(String id) {
        return crudGhost.find(id);
    }

    @Override
    public DGhost insert(DGhost domain) {
        validateDuplicatedResource(domain);
        domain.validate();
        return crudGhost.insert(domain);
    }

    @Override
    public DGhost update(String id, DGhost domain) {
        validateDuplicatedResource(domain);
        domain.validate();
        return crudGhost.update(id, domain);
    }

    @Override
    public void inactivate(String id) {
        crudGhost.inactivate(id);
    }

    @Override
    public void delete(String id) {
        crudGhost.delete(id);
    }

    private void validateDuplicatedResource(DGhost domain){
        if(crudGhost.findByDescription(domain.getDescription())
                .stream()
                .anyMatch(t -> !t.getCode().equals(domain.getCode()))){
            throw new DuplicatedResourceException("Verifique o campo descrição.");
        }
    }
}
