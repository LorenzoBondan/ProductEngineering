package br.com.todeschini.domain.business.packaging.usednonwovenfabric;

import br.com.todeschini.domain.business.packaging.usednonwovenfabric.api.UsedNonwovenFabricService;
import br.com.todeschini.domain.business.packaging.usednonwovenfabric.spi.CrudUsedNonwovenFabric;
import br.com.todeschini.domain.exceptions.UniqueConstraintViolationException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class UsedNonwovenFabricServiceImpl implements UsedNonwovenFabricService {

    private final CrudUsedNonwovenFabric crudUsedNonwovenFabric;

    public UsedNonwovenFabricServiceImpl(CrudUsedNonwovenFabric crudUsedNonwovenFabric) {
        this.crudUsedNonwovenFabric = crudUsedNonwovenFabric;
    }

    @Override
    public List<DUsedNonwovenFabric> findAllActiveAndCurrentOne(Long id) {
        return crudUsedNonwovenFabric.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DUsedNonwovenFabric find(Long id) {
        return crudUsedNonwovenFabric.find(id);
    }

    @Override
    public DUsedNonwovenFabric insert(DUsedNonwovenFabric domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudUsedNonwovenFabric.insert(domain);
    }

    @Override
    public DUsedNonwovenFabric update(Long id, DUsedNonwovenFabric domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudUsedNonwovenFabric.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudUsedNonwovenFabric.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudUsedNonwovenFabric.delete(id);
    }

    private void validateDuplicatedResource(DUsedNonwovenFabric domain){
        if(crudUsedNonwovenFabric.findByNonwovenFabricAndGhost(domain.getNonwovenFabricCode(), domain.getGhostCode())
                .stream()
                .anyMatch(t -> !t.getId().equals(Optional.ofNullable(domain.getId()).orElse(-1L)))){
            throw new UniqueConstraintViolationException("Registro duplicado para a combinação de Poliéster e Fantasma.");
        }
    }
}
