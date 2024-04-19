package br.com.todeschini.domain.business.mdf.usedbacksheet;

import br.com.todeschini.domain.business.mdf.usedbacksheet.api.UsedBackSheetService;
import br.com.todeschini.domain.business.mdf.usedbacksheet.spi.CrudUsedBackSheet;
import br.com.todeschini.domain.exceptions.UniqueConstraintViolationException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class UsedBackSheetServiceImpl implements UsedBackSheetService {

    private final CrudUsedBackSheet crudUsedBackSheet;

    public UsedBackSheetServiceImpl(CrudUsedBackSheet crudUsedBackSheet) {
        this.crudUsedBackSheet = crudUsedBackSheet;
    }

    @Override
    public List<DUsedBackSheet> findAllActiveAndCurrentOne(Long id) {
        return crudUsedBackSheet.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DUsedBackSheet find(Long id) {
        return crudUsedBackSheet.find(id);
    }

    @Override
    public DUsedBackSheet insert(DUsedBackSheet domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudUsedBackSheet.insert(domain);
    }

    @Override
    public DUsedBackSheet update(Long id, DUsedBackSheet domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudUsedBackSheet.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudUsedBackSheet.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudUsedBackSheet.delete(id);
    }

    private void validateDuplicatedResource(DUsedBackSheet domain){
        if(crudUsedBackSheet.findByBackAndSheet(domain.getBackCode(), domain.getSheetCode())
                .stream()
                .anyMatch(t -> !t.getId().equals(Optional.ofNullable(domain.getId()).orElse(-1L)))){
            String detailedMessage = "Registro duplicado para a combinação de Fundo e Chapa.";
            throw new UniqueConstraintViolationException("chk_used_back_sheet_uk", detailedMessage);
        }
    }
}
