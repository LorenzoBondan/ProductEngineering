package br.com.todeschini.domain.business.mdp.usedglue;

import br.com.todeschini.domain.business.mdp.usedglue.api.UsedGlueService;
import br.com.todeschini.domain.business.mdp.usedglue.spi.CrudUsedGlue;
import br.com.todeschini.domain.exceptions.UniqueConstraintViolationException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class UsedGlueServiceImpl implements UsedGlueService {

    private final CrudUsedGlue crudUsedGlue;

    public UsedGlueServiceImpl(CrudUsedGlue crudUsedGlue) {
        this.crudUsedGlue = crudUsedGlue;
    }

    @Override
    public List<DUsedGlue> findAllActiveAndCurrentOne(Long id) {
        return crudUsedGlue.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DUsedGlue find(Long id) {
        return crudUsedGlue.find(id);
    }

    @Override
    public DUsedGlue insert(DUsedGlue domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudUsedGlue.insert(domain);
    }

    @Override
    public DUsedGlue update(Long id, DUsedGlue domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudUsedGlue.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudUsedGlue.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudUsedGlue.delete(id);
    }

    private void validateDuplicatedResource(DUsedGlue domain){
        if(crudUsedGlue.findByGlueAndMDPSon(domain.getGlueCode(), domain.getMdpSonCode())
                .stream()
                .anyMatch(t -> !t.getId().equals(Optional.ofNullable(domain.getId()).orElse(-1L)))){
            throw new UniqueConstraintViolationException("Registro duplicado para a combinação de Cola e Filho.");
        }
    }
}
