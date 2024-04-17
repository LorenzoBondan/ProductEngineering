package br.com.todeschini.domain.business.aluminium.screw;

import br.com.todeschini.domain.business.aluminium.screw.api.ScrewService;
import br.com.todeschini.domain.business.aluminium.screw.spi.CrudScrew;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class ScrewServiceImpl implements ScrewService {

    private final CrudScrew crudScrew;

    public ScrewServiceImpl(CrudScrew crudScrew) {
        this.crudScrew = crudScrew;
    }

    @Override
    public List<DScrew> findAllActiveAndCurrentOne(Long id) {
        return crudScrew.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DScrew find(Long id) {
        return crudScrew.find(id);
    }

    @Override
    public DScrew insert(DScrew domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudScrew.insert(domain);
    }

    @Override
    public DScrew update(Long id, DScrew domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudScrew.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudScrew.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudScrew.delete(id);
    }

    private void validateDuplicatedResource(DScrew domain){
        if(crudScrew.findByDescription(domain.getDescription())
                .stream()
                .anyMatch(t -> !t.getCode().equals(Optional.ofNullable(domain.getCode()).orElse(-1L)))){
            throw new DuplicatedResourceException("Verifique o campo descrição.");
        }
    }
}
