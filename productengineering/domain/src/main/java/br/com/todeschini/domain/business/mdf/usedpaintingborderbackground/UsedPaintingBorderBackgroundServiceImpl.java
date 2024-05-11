package br.com.todeschini.domain.business.mdf.usedpaintingborderbackground;

import br.com.todeschini.domain.business.mdf.usedpaintingborderbackground.api.UsedPaintingBorderBackgroundService;
import br.com.todeschini.domain.business.mdf.usedpaintingborderbackground.spi.CrudUsedPaintingBorderBackground;
import br.com.todeschini.domain.exceptions.UniqueConstraintViolationException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class UsedPaintingBorderBackgroundServiceImpl implements UsedPaintingBorderBackgroundService {

    private final CrudUsedPaintingBorderBackground crudUsedPaintingBorderBackground;

    public UsedPaintingBorderBackgroundServiceImpl(CrudUsedPaintingBorderBackground crudUsedPaintingBorderBackground) {
        this.crudUsedPaintingBorderBackground = crudUsedPaintingBorderBackground;
    }

    @Override
    public List<DUsedPaintingBorderBackground> findAllActiveAndCurrentOne(Long id) {
        return crudUsedPaintingBorderBackground.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DUsedPaintingBorderBackground find(Long id) {
        return crudUsedPaintingBorderBackground.find(id);
    }

    @Override
    public DUsedPaintingBorderBackground insert(DUsedPaintingBorderBackground domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudUsedPaintingBorderBackground.insert(domain);
    }

    @Override
    public DUsedPaintingBorderBackground update(Long id, DUsedPaintingBorderBackground domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudUsedPaintingBorderBackground.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudUsedPaintingBorderBackground.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudUsedPaintingBorderBackground.delete(id);
    }

    private void validateDuplicatedResource(DUsedPaintingBorderBackground domain){
        if(crudUsedPaintingBorderBackground.findByPaintingBorderBackgroundAndPaintingSon(domain.getPaintingBorderBackgroundCode(), domain.getPaintingSonCode())
                .stream()
                .anyMatch(t -> !t.getId().equals(Optional.ofNullable(domain.getId()).orElse(-1L)))){
            throw new UniqueConstraintViolationException("Registro duplicado para a combinação de PaintingBorderBackground e Filho.");
        }
    }
}
