package br.com.todeschini.domain.business.publico.color;

import br.com.todeschini.domain.business.publico.color.api.ColorService;
import br.com.todeschini.domain.business.publico.color.spi.CrudColor;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class ColorServiceImpl implements ColorService {

    private final CrudColor crudColor;

    public ColorServiceImpl(CrudColor crudColor) {
        this.crudColor = crudColor;
    }

    @Override
    public List<DColor> findAllActiveAndCurrentOne(Long id) {
        return crudColor.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DColor find(Long id) {
        return crudColor.find(id);
    }

    @Override
    public DColor insert(DColor domain) {
        validateDuplicatedResource(domain);
        domain.validate();
        return crudColor.insert(domain);
    }

    @Override
    public DColor update(Long id, DColor domain) {
        validateDuplicatedResource(domain);
        domain.validate();
        return crudColor.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudColor.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudColor.delete(id);
    }

    private void validateDuplicatedResource(DColor domain){
        if(crudColor.findByName(domain.getName())
                .stream()
                .anyMatch(t -> !t.getCode().equals(Optional.ofNullable(domain.getCode()).orElse(-1L)))){
            throw new DuplicatedResourceException("Verifique o campo nome.");
        }
    }
}
