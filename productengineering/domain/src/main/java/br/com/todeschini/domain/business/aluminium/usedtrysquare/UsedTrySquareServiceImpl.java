package br.com.todeschini.domain.business.aluminium.usedtrysquare;

import br.com.todeschini.domain.business.aluminium.usedtrysquare.api.UsedTrySquareService;
import br.com.todeschini.domain.business.aluminium.usedtrysquare.spi.CrudUsedTrySquare;
import br.com.todeschini.domain.exceptions.UniqueConstraintViolationException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class UsedTrySquareServiceImpl implements UsedTrySquareService {

    private final CrudUsedTrySquare crudUsedTrySquare;

    public UsedTrySquareServiceImpl(CrudUsedTrySquare crudUsedTrySquare) {
        this.crudUsedTrySquare = crudUsedTrySquare;
    }

    @Override
    public List<DUsedTrySquare> findAllActiveAndCurrentOne(Long id) {
        return crudUsedTrySquare.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DUsedTrySquare find(Long id) {
        return crudUsedTrySquare.find(id);
    }

    @Override
    public DUsedTrySquare insert(DUsedTrySquare domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudUsedTrySquare.insert(domain);
    }

    @Override
    public DUsedTrySquare update(Long id, DUsedTrySquare domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudUsedTrySquare.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudUsedTrySquare.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudUsedTrySquare.delete(id);
    }

    private void validateDuplicatedResource(DUsedTrySquare domain){
        if(crudUsedTrySquare.findByTrySquareAndAluminiumSon(domain.getTrySquareCode(), domain.getAluminiumSonCode())
                .stream()
                .anyMatch(t -> !t.getId().equals(Optional.ofNullable(domain.getId()).orElse(-1L)))){
            String detailedMessage = "Registro duplicado para a combinação de Esquadreta e Filho.";
            throw new UniqueConstraintViolationException("chk_used_try_square_unq", detailedMessage);
        }
    }
}
