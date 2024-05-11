package br.com.todeschini.domain.business.mdp.usedsheet;

import br.com.todeschini.domain.business.mdp.usedsheet.api.UsedSheetService;
import br.com.todeschini.domain.business.mdp.usedsheet.spi.CrudUsedSheet;
import br.com.todeschini.domain.exceptions.UniqueConstraintViolationException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class UsedSheetServiceImpl implements UsedSheetService {

    private final CrudUsedSheet crudUsedSheet;

    public UsedSheetServiceImpl(CrudUsedSheet crudUsedSheet) {
        this.crudUsedSheet = crudUsedSheet;
    }

    @Override
    public List<DUsedSheet> findAllActiveAndCurrentOne(Long id) {
        return crudUsedSheet.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DUsedSheet find(Long id) {
        return crudUsedSheet.find(id);
    }

    @Override
    public DUsedSheet insert(DUsedSheet domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudUsedSheet.insert(domain);
    }

    @Override
    public DUsedSheet update(Long id, DUsedSheet domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudUsedSheet.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudUsedSheet.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudUsedSheet.delete(id);
    }

    private void validateDuplicatedResource(DUsedSheet domain){
        if(crudUsedSheet.findBySheetAndMDPSon(domain.getSheetCode(), domain.getMdpSonCode())
                .stream()
                .anyMatch(t -> !t.getId().equals(Optional.ofNullable(domain.getId()).orElse(-1L)))){
            throw new UniqueConstraintViolationException("Registro duplicado para a combinação de Chapa e Filho.");
        }
    }
}
