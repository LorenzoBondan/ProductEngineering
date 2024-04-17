package br.com.todeschini.domain.business.mdp.glue;

import br.com.todeschini.domain.business.mdp.glue.api.GlueService;
import br.com.todeschini.domain.business.mdp.glue.spi.CrudGlue;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class GlueServiceImpl implements GlueService {

    private final CrudGlue crudGlue;

    public GlueServiceImpl(CrudGlue crudGlue) {
        this.crudGlue = crudGlue;
    }

    @Override
    public List<DGlue> findAllActiveAndCurrentOne(Long id) {
        return crudGlue.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DGlue find(Long id) {
        return crudGlue.find(id);
    }

    @Override
    public DGlue insert(DGlue domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudGlue.insert(domain);
    }

    @Override
    public DGlue update(Long id, DGlue domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudGlue.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudGlue.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudGlue.delete(id);
    }

    private void validateDuplicatedResource(DGlue domain){
        if(crudGlue.findByDescription(domain.getDescription())
                .stream()
                .anyMatch(t -> !t.getCode().equals(Optional.ofNullable(domain.getCode()).orElse(-1L)))){
            throw new DuplicatedResourceException("Verifique o campo descrição.");
        }
    }
}
