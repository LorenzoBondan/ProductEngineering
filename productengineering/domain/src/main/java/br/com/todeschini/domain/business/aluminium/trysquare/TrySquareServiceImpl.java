package br.com.todeschini.domain.business.aluminium.trysquare;

import br.com.todeschini.domain.business.aluminium.trysquare.api.TrySquareService;
import br.com.todeschini.domain.business.aluminium.trysquare.spi.CrudTrySquare;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class TrySquareServiceImpl implements TrySquareService {

    private final CrudTrySquare crudTrySquare;

    public TrySquareServiceImpl(CrudTrySquare crudTrySquare) {
        this.crudTrySquare = crudTrySquare;
    }

    @Override
    public List<DTrySquare> findAllActiveAndCurrentOne(Long id) {
        return crudTrySquare.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DTrySquare find(Long id) {
        return crudTrySquare.find(id);
    }

    @Override
    public DTrySquare insert(DTrySquare domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudTrySquare.insert(domain);
    }

    @Override
    public DTrySquare update(Long id, DTrySquare domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudTrySquare.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudTrySquare.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudTrySquare.delete(id);
    }

    private void validateDuplicatedResource(DTrySquare domain){
        if(crudTrySquare.findByDescription(domain.getDescription())
                .stream()
                .anyMatch(t -> !t.getCode().equals(Optional.ofNullable(domain.getCode()).orElse(-1L)))){
            throw new DuplicatedResourceException("Verifique o campo descrição.");
        }
    }
}
