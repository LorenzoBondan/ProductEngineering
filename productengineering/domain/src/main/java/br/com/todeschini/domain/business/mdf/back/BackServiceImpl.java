package br.com.todeschini.domain.business.mdf.back;

import br.com.todeschini.domain.business.mdf.back.api.BackService;
import br.com.todeschini.domain.business.mdf.back.spi.CrudBack;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class BackServiceImpl implements BackService {

    private final CrudBack crudBack;

    public BackServiceImpl(CrudBack crudBack) {
        this.crudBack = crudBack;
    }

    @Override
    public List<DBack> findAllActiveAndCurrentOne(Long id) {
        return crudBack.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DBack find(Long id) {
        return crudBack.find(id);
    }

    @Override
    public DBack insert(DBack domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudBack.insert(domain);
    }

    @Override
    public DBack update(Long id, DBack domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudBack.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudBack.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudBack.delete(id);
    }

    private void validateDuplicatedResource(DBack domain){
        if(crudBack.findByDescription(domain.getDescription())
                .stream()
                .anyMatch(t -> !t.getCode().equals(Optional.ofNullable(domain.getCode()).orElse(-1L)))){
            throw new DuplicatedResourceException("Verifique o campo descrição.");
        }
    }
}
