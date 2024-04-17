package br.com.todeschini.domain.business.mdf.paintingborderbackground;

import br.com.todeschini.domain.business.mdf.paintingborderbackground.api.PaintingBorderBackgroundService;
import br.com.todeschini.domain.business.mdf.paintingborderbackground.spi.CrudPaintingBorderBackground;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class PaintingBorderBackgroundServiceImpl implements PaintingBorderBackgroundService {

    private final CrudPaintingBorderBackground crudPaintingBorderBackground;

    public PaintingBorderBackgroundServiceImpl(CrudPaintingBorderBackground crudPaintingBorderBackground) {
        this.crudPaintingBorderBackground = crudPaintingBorderBackground;
    }

    @Override
    public List<DPaintingBorderBackground> findAllActiveAndCurrentOne(Long id) {
        return crudPaintingBorderBackground.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DPaintingBorderBackground find(Long id) {
        return crudPaintingBorderBackground.find(id);
    }

    @Override
    public DPaintingBorderBackground insert(DPaintingBorderBackground domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudPaintingBorderBackground.insert(domain);
    }

    @Override
    public DPaintingBorderBackground update(Long id, DPaintingBorderBackground domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudPaintingBorderBackground.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudPaintingBorderBackground.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudPaintingBorderBackground.delete(id);
    }

    private void validateDuplicatedResource(DPaintingBorderBackground domain){
        if(crudPaintingBorderBackground.findByDescription(domain.getDescription())
                .stream()
                .anyMatch(t -> !t.getCode().equals(Optional.ofNullable(domain.getCode()).orElse(-1L)))){
            throw new DuplicatedResourceException("Verifique o campo descrição.");
        }
    }
}
