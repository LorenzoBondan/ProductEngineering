package br.com.todeschini.domain.business.publico.item;

import br.com.todeschini.domain.business.publico.item.api.ItemService;
import br.com.todeschini.domain.business.publico.item.spi.CrudItem;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class ItemServiceImpl implements ItemService {

    private final CrudItem crudItem;

    public ItemServiceImpl(CrudItem crudItem) {
        this.crudItem = crudItem;
    }

    @Override
    public List<DItem> findAllActiveAndCurrentOne(Long id) {
        return crudItem.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DItem find(Long id) {
        return crudItem.find(id);
    }

    @Override
    public DItem insert(DItem domain) {
        validateDuplicatedResource(domain);
        domain.validate();
        return crudItem.insert(domain);
    }

    @Override
    public DItem update(Long id, DItem domain) {
        validateDuplicatedResource(domain);
        domain.validate();
        return crudItem.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudItem.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudItem.delete(id);
    }

    private void validateDuplicatedResource(DItem domain){
        if(crudItem.findByDescription(domain.getDescription())
                .stream()
                .anyMatch(t -> !t.getCode().equals(Optional.ofNullable(domain.getCode()).orElse(-1L)))){
            throw new DuplicatedResourceException("Verifique o campo descrição.");
        }
    }
}
