package br.com.todeschini.domain.business.packaging.usedpolyethylene;

import br.com.todeschini.domain.business.packaging.usedpolyethylene.api.UsedPolyethyleneService;
import br.com.todeschini.domain.business.packaging.usedpolyethylene.spi.CrudUsedPolyethylene;
import br.com.todeschini.domain.exceptions.UniqueConstraintViolationException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class UsedPolyethyleneServiceImpl implements UsedPolyethyleneService {

    private final CrudUsedPolyethylene crudUsedPolyethylene;

    public UsedPolyethyleneServiceImpl(CrudUsedPolyethylene crudUsedPolyethylene) {
        this.crudUsedPolyethylene = crudUsedPolyethylene;
    }

    @Override
    public List<DUsedPolyethylene> findAllActiveAndCurrentOne(Long id) {
        return crudUsedPolyethylene.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DUsedPolyethylene find(Long id) {
        return crudUsedPolyethylene.find(id);
    }

    @Override
    public DUsedPolyethylene insert(DUsedPolyethylene domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudUsedPolyethylene.insert(domain);
    }

    @Override
    public DUsedPolyethylene update(Long id, DUsedPolyethylene domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudUsedPolyethylene.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudUsedPolyethylene.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudUsedPolyethylene.delete(id);
    }

    private void validateDuplicatedResource(DUsedPolyethylene domain){
        if(crudUsedPolyethylene.findByPolyethyleneAndGhost(domain.getPolyethyleneCode(), domain.getGhostCode())
                .stream()
                .anyMatch(t -> !t.getId().equals(Optional.ofNullable(domain.getId()).orElse(-1L)))){
            String detailedMessage = "Registro duplicado para a combinação de Polietileno e Fantasma.";
            throw new UniqueConstraintViolationException("chk_used_polyethylene_unq", detailedMessage);
        }
    }
}
