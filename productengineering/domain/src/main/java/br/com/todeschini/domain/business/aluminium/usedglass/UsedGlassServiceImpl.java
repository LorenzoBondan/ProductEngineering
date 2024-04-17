package br.com.todeschini.domain.business.aluminium.usedglass;

import br.com.todeschini.domain.business.aluminium.usedglass.api.UsedGlassService;
import br.com.todeschini.domain.business.aluminium.usedglass.spi.CrudUsedGlass;
import br.com.todeschini.domain.exceptions.UniqueConstraintViolationException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class UsedGlassServiceImpl implements UsedGlassService {

    private final CrudUsedGlass crudUsedGlass;

    public UsedGlassServiceImpl(CrudUsedGlass crudUsedGlass) {
        this.crudUsedGlass = crudUsedGlass;
    }

    @Override
    public List<DUsedGlass> findAllActiveAndCurrentOne(Long id) {
        return crudUsedGlass.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DUsedGlass find(Long id) {
        return crudUsedGlass.find(id);
    }

    @Override
    public DUsedGlass insert(DUsedGlass domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudUsedGlass.insert(domain);
    }

    @Override
    public DUsedGlass update(Long id, DUsedGlass domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudUsedGlass.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudUsedGlass.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudUsedGlass.delete(id);
    }

    private void validateDuplicatedResource(DUsedGlass domain){
        if(crudUsedGlass.findByGlassAndAluminiumSon(domain.getGlassCode(), domain.getAluminiumSonCode())
                .stream()
                .anyMatch(t -> !t.getId().equals(Optional.ofNullable(domain.getId()).orElse(-1L)))){
            String detailedMessage = "Registro duplicado para a combinação de Vidro e Filho.";
            throw new UniqueConstraintViolationException("chk_used_glass_unq", detailedMessage);
        }
    }
}
