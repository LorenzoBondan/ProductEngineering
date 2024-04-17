package br.com.todeschini.domain.business.aluminium.molding;

import br.com.todeschini.domain.business.aluminium.molding.api.MoldingService;
import br.com.todeschini.domain.business.aluminium.molding.spi.CrudMolding;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class MoldingServiceImpl implements MoldingService {

    private final CrudMolding crudMolding;

    public MoldingServiceImpl(CrudMolding crudMolding) {
        this.crudMolding = crudMolding;
    }

    @Override
    public List<DMolding> findAllActiveAndCurrentOne(Long id) {
        return crudMolding.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DMolding find(Long id) {
        return crudMolding.find(id);
    }

    @Override
    public DMolding insert(DMolding domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudMolding.insert(domain);
    }

    @Override
    public DMolding update(Long id, DMolding domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudMolding.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudMolding.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudMolding.delete(id);
    }

    private void validateDuplicatedResource(DMolding domain){
        if(crudMolding.findByDescription(domain.getDescription())
                .stream()
                .anyMatch(t -> !t.getCode().equals(Optional.ofNullable(domain.getCode()).orElse(-1L)))){
            throw new DuplicatedResourceException("Verifique o campo descrição.");
        }
    }
}
