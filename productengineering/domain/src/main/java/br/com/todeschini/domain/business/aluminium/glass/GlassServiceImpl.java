package br.com.todeschini.domain.business.aluminium.glass;

import br.com.todeschini.domain.business.aluminium.glass.api.GlassService;
import br.com.todeschini.domain.business.aluminium.glass.spi.CrudGlass;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class GlassServiceImpl implements GlassService {

    private final CrudGlass crudGlass;

    public GlassServiceImpl(CrudGlass crudGlass) {
        this.crudGlass = crudGlass;
    }

    @Override
    public List<DGlass> findAllActiveAndCurrentOne(Long id) {
        return crudGlass.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DGlass find(Long id) {
        return crudGlass.find(id);
    }

    @Override
    public DGlass insert(DGlass domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudGlass.insert(domain);
    }

    @Override
    public DGlass update(Long id, DGlass domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudGlass.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudGlass.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudGlass.delete(id);
    }

    private void validateDuplicatedResource(DGlass domain){
        if(crudGlass.findByDescription(domain.getDescription())
                .stream()
                .anyMatch(t -> !t.getCode().equals(Optional.ofNullable(domain.getCode()).orElse(-1L)))){
            throw new DuplicatedResourceException("Verifique o campo descrição.");
        }
    }
}
