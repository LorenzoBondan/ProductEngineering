package br.com.todeschini.domain.business.aluminium.usedscrew;

import br.com.todeschini.domain.business.aluminium.usedscrew.api.UsedScrewService;
import br.com.todeschini.domain.business.aluminium.usedscrew.spi.CrudUsedScrew;
import br.com.todeschini.domain.exceptions.UniqueConstraintViolationException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class UsedScrewServiceImpl implements UsedScrewService {

    private final CrudUsedScrew crudUsedScrew;

    public UsedScrewServiceImpl(CrudUsedScrew crudUsedScrew) {
        this.crudUsedScrew = crudUsedScrew;
    }

    @Override
    public List<DUsedScrew> findAllActiveAndCurrentOne(Long id) {
        return crudUsedScrew.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DUsedScrew find(Long id) {
        return crudUsedScrew.find(id);
    }

    @Override
    public DUsedScrew insert(DUsedScrew domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudUsedScrew.insert(domain);
    }

    @Override
    public DUsedScrew update(Long id, DUsedScrew domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudUsedScrew.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudUsedScrew.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudUsedScrew.delete(id);
    }

    private void validateDuplicatedResource(DUsedScrew domain){
        if(crudUsedScrew.findByScrewAndAluminiumSon(domain.getScrewCode(), domain.getAluminiumSonCode())
                .stream()
                .anyMatch(t -> !t.getId().equals(Optional.ofNullable(domain.getId()).orElse(-1L)))){
            String detailedMessage = "Registro duplicado para a combinação de Parafuso e Filho.";
            throw new UniqueConstraintViolationException("chk_used_screw_unq", detailedMessage);
        }
    }
}
