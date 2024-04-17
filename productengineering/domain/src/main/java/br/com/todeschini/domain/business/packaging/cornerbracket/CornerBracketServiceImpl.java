package br.com.todeschini.domain.business.packaging.cornerbracket;

import br.com.todeschini.domain.business.packaging.cornerbracket.api.CornerBracketService;
import br.com.todeschini.domain.business.packaging.cornerbracket.spi.CrudCornerBracket;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class CornerBracketServiceImpl implements CornerBracketService {

    private final CrudCornerBracket crudCornerBracket;

    public CornerBracketServiceImpl(CrudCornerBracket crudCornerBracket) {
        this.crudCornerBracket = crudCornerBracket;
    }

    @Override
    public List<DCornerBracket> findAllActiveAndCurrentOne(Long id) {
        return crudCornerBracket.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DCornerBracket find(Long id) {
        return crudCornerBracket.find(id);
    }

    @Override
    public DCornerBracket insert(DCornerBracket domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudCornerBracket.insert(domain);
    }

    @Override
    public DCornerBracket update(Long id, DCornerBracket domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudCornerBracket.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudCornerBracket.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudCornerBracket.delete(id);
    }

    private void validateDuplicatedResource(DCornerBracket domain){
        if(crudCornerBracket.findByDescription(domain.getDescription())
                .stream()
                .anyMatch(t -> !t.getCode().equals(Optional.ofNullable(domain.getCode()).orElse(-1L)))){
            throw new DuplicatedResourceException("Verifique o campo descrição.");
        }
    }
}
