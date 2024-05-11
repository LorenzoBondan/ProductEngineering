package br.com.todeschini.domain.business.packaging.usedplastic;

import br.com.todeschini.domain.business.packaging.usedplastic.api.UsedPlasticService;
import br.com.todeschini.domain.business.packaging.usedplastic.spi.CrudUsedPlastic;
import br.com.todeschini.domain.exceptions.UniqueConstraintViolationException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class UsedPlasticServiceImpl implements UsedPlasticService {

    private final CrudUsedPlastic crudUsedPlastic;

    public UsedPlasticServiceImpl(CrudUsedPlastic crudUsedPlastic) {
        this.crudUsedPlastic = crudUsedPlastic;
    }

    @Override
    public List<DUsedPlastic> findAllActiveAndCurrentOne(Long id) {
        return crudUsedPlastic.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DUsedPlastic find(Long id) {
        return crudUsedPlastic.find(id);
    }

    @Override
    public DUsedPlastic insert(DUsedPlastic domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudUsedPlastic.insert(domain);
    }

    @Override
    public DUsedPlastic update(Long id, DUsedPlastic domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudUsedPlastic.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudUsedPlastic.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudUsedPlastic.delete(id);
    }

    private void validateDuplicatedResource(DUsedPlastic domain){
        if(crudUsedPlastic.findByPlasticAndGhost(domain.getPlasticCode(), domain.getGhostCode())
                .stream()
                .anyMatch(t -> !t.getId().equals(Optional.ofNullable(domain.getId()).orElse(-1L)))){
            throw new UniqueConstraintViolationException("Registro duplicado para a combinação de Plástico e Fantasma.");
        }
    }
}
