package br.com.todeschini.domain.business.publico.son;

import br.com.todeschini.domain.business.publico.son.api.SonService;
import br.com.todeschini.domain.business.publico.son.spi.CrudSon;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class SonServiceImpl implements SonService {

    private final CrudSon crudSon;

    public SonServiceImpl(CrudSon crudSon) {
        this.crudSon = crudSon;
    }

    @Override
    public List<DSon> findAllActiveAndCurrentOne(Long id) {
        return crudSon.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DSon find(Long id) {
        return crudSon.find(id);
    }

    @Override
    public DSon insert(DSon domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudSon.insert(domain);
    }

    @Override
    public DSon update(Long id, DSon domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudSon.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudSon.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudSon.delete(id);
    }

    private void validateDuplicatedResource(DSon domain){
        if(crudSon.findByDescription(domain.getDescription())
                .stream()
                .anyMatch(t -> !t.getCode().equals(Optional.ofNullable(domain.getCode()).orElse(-1L)))){
            throw new DuplicatedResourceException("Verifique o campo descrição.");
        }
    }
}
