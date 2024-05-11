package br.com.todeschini.domain.business.aluminium.usedmolding;

import br.com.todeschini.domain.business.aluminium.usedmolding.api.UsedMoldingService;
import br.com.todeschini.domain.business.aluminium.usedmolding.spi.CrudUsedMolding;
import br.com.todeschini.domain.exceptions.UniqueConstraintViolationException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class UsedMoldingServiceImpl implements UsedMoldingService {

    private final CrudUsedMolding crudUsedMolding;

    public UsedMoldingServiceImpl(CrudUsedMolding crudUsedMolding) {
        this.crudUsedMolding = crudUsedMolding;
    }

    @Override
    public List<DUsedMolding> findAllActiveAndCurrentOne(Long id) {
        return crudUsedMolding.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DUsedMolding find(Long id) {
        return crudUsedMolding.find(id);
    }

    @Override
    public DUsedMolding insert(DUsedMolding domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudUsedMolding.insert(domain);
    }

    @Override
    public DUsedMolding update(Long id, DUsedMolding domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudUsedMolding.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudUsedMolding.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudUsedMolding.delete(id);
    }

    private void validateDuplicatedResource(DUsedMolding domain){
        if(crudUsedMolding.findByMoldingAndAluminiumSon(domain.getMoldingCode(), domain.getAluminiumSonCode())
                .stream()
                .anyMatch(t -> !t.getId().equals(Optional.ofNullable(domain.getId()).orElse(-1L)))){
            throw new UniqueConstraintViolationException("Registro duplicado para a combinação de Perfil e Filho.");
        }
    }
}
