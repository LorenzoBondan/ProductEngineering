package br.com.todeschini.domain.business.mdf.polyester;

import br.com.todeschini.domain.business.mdf.polyester.api.PolyesterService;
import br.com.todeschini.domain.business.mdf.polyester.spi.CrudPolyester;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class PolyesterServiceImpl implements PolyesterService {

    private final CrudPolyester crudPolyester;

    public PolyesterServiceImpl(CrudPolyester crudPolyester) {
        this.crudPolyester = crudPolyester;
    }

    @Override
    public List<DPolyester> findAllActiveAndCurrentOne(Long id) {
        return crudPolyester.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DPolyester find(Long id) {
        return crudPolyester.find(id);
    }

    @Override
    public DPolyester insert(DPolyester domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudPolyester.insert(domain);
    }

    @Override
    public DPolyester update(Long id, DPolyester domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudPolyester.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudPolyester.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudPolyester.delete(id);
    }

    private void validateDuplicatedResource(DPolyester domain){
        if(crudPolyester.findByDescription(domain.getDescription())
                .stream()
                .anyMatch(t -> !t.getCode().equals(Optional.ofNullable(domain.getCode()).orElse(-1L)))){
            throw new DuplicatedResourceException("Verifique o campo descrição.");
        }
    }
}
