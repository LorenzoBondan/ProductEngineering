package br.com.todeschini.domain.business.packaging.usedcornerbracket;

import br.com.todeschini.domain.business.packaging.usedcornerbracket.api.UsedCornerBracketService;
import br.com.todeschini.domain.business.packaging.usedcornerbracket.spi.CrudUsedCornerBracket;
import br.com.todeschini.domain.exceptions.UniqueConstraintViolationException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class UsedCornerBracketServiceImpl implements UsedCornerBracketService {

    private final CrudUsedCornerBracket crudUsedCornerBracket;

    public UsedCornerBracketServiceImpl(CrudUsedCornerBracket crudUsedCornerBracket) {
        this.crudUsedCornerBracket = crudUsedCornerBracket;
    }

    @Override
    public List<DUsedCornerBracket> findAllActiveAndCurrentOne(Long id) {
        return crudUsedCornerBracket.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DUsedCornerBracket find(Long id) {
        return crudUsedCornerBracket.find(id);
    }

    @Override
    public DUsedCornerBracket insert(DUsedCornerBracket domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudUsedCornerBracket.insert(domain);
    }

    @Override
    public DUsedCornerBracket update(Long id, DUsedCornerBracket domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudUsedCornerBracket.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudUsedCornerBracket.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudUsedCornerBracket.delete(id);
    }

    private void validateDuplicatedResource(DUsedCornerBracket domain){
        if(crudUsedCornerBracket.findByCornerBracketAndGhost(domain.getCornerBracketCode(), domain.getGhostCode())
                .stream()
                .anyMatch(t -> !t.getId().equals(Optional.ofNullable(domain.getId()).orElse(-1L)))){
            throw new UniqueConstraintViolationException("Registro duplicado para a combinação de Cantoneira e Fantasma.");
        }
    }
}
