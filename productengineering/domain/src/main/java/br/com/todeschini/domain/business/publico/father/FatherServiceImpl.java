package br.com.todeschini.domain.business.publico.father;

import br.com.todeschini.domain.business.publico.father.api.FatherService;
import br.com.todeschini.domain.business.publico.father.spi.CrudFather;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class FatherServiceImpl implements FatherService {

    private final CrudFather crudFather;

    public FatherServiceImpl(CrudFather crudFather) {
        this.crudFather = crudFather;
    }

    @Override
    public List<DFather> findAllActiveAndCurrentOne(Long id) {
        return crudFather.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DFather find(Long id) {
        return crudFather.find(id);
    }

    @Override
    public DFather insert(DFather domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudFather.insert(domain);
    }

    @Override
    public DFather update(Long id, DFather domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudFather.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudFather.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudFather.delete(id);
    }

    private void validateDuplicatedResource(DFather domain){
        if(crudFather.findByDescription(domain.getDescription())
                .stream()
                .anyMatch(t -> !t.getCode().equals(Optional.ofNullable(domain.getCode()).orElse(-1L)))){
            throw new DuplicatedResourceException("Verifique o campo descrição.");
        }
    }
}
