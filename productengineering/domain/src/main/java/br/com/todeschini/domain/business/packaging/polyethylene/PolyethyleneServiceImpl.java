package br.com.todeschini.domain.business.packaging.polyethylene;

import br.com.todeschini.domain.business.packaging.polyethylene.api.PolyethyleneService;
import br.com.todeschini.domain.business.packaging.polyethylene.spi.CrudPolyethylene;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class PolyethyleneServiceImpl implements PolyethyleneService {

    private final CrudPolyethylene crudPolyethylene;

    public PolyethyleneServiceImpl(CrudPolyethylene crudPolyethylene) {
        this.crudPolyethylene = crudPolyethylene;
    }

    @Override
    public List<DPolyethylene> findAllActiveAndCurrentOne(Long id) {
        return crudPolyethylene.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DPolyethylene find(Long id) {
        return crudPolyethylene.find(id);
    }

    @Override
    public DPolyethylene insert(DPolyethylene domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudPolyethylene.insert(domain);
    }

    @Override
    public DPolyethylene update(Long id, DPolyethylene domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudPolyethylene.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudPolyethylene.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudPolyethylene.delete(id);
    }

    private void validateDuplicatedResource(DPolyethylene domain){
        if(crudPolyethylene.findByDescription(domain.getDescription())
                .stream()
                .anyMatch(t -> !t.getCode().equals(Optional.ofNullable(domain.getCode()).orElse(-1L)))){
            throw new DuplicatedResourceException("Verifique o campo descrição.");
        }
    }
}
