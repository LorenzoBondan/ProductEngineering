package br.com.todeschini.domain.business.mdp.sheet;

import br.com.todeschini.domain.business.mdp.sheet.api.SheetService;
import br.com.todeschini.domain.business.mdp.sheet.spi.CrudSheet;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class SheetServiceImpl implements SheetService {

    private final CrudSheet crudSheet;

    public SheetServiceImpl(CrudSheet crudSheet) {
        this.crudSheet = crudSheet;
    }

    @Override
    public List<DSheet> findAllActiveAndCurrentOne(Long id) {
        return crudSheet.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DSheet find(Long id) {
        return crudSheet.find(id);
    }

    @Override
    public DSheet insert(DSheet domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudSheet.insert(domain);
    }

    @Override
    public DSheet update(Long id, DSheet domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudSheet.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudSheet.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudSheet.delete(id);
    }

    private void validateDuplicatedResource(DSheet domain){
        if(crudSheet.findByDescription(domain.getDescription())
                .stream()
                .anyMatch(t -> !t.getCode().equals(Optional.ofNullable(domain.getCode()).orElse(-1L)))){
            throw new DuplicatedResourceException("Verifique o campo descrição.");
        }
    }
}
