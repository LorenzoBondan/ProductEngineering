package br.com.todeschini.domain.business.mdf.paintingtype;

import br.com.todeschini.domain.business.mdf.paintingtype.api.PaintingTypeService;
import br.com.todeschini.domain.business.mdf.paintingtype.spi.CrudPaintingType;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class PaintingTypeServiceImpl implements PaintingTypeService {

    private final CrudPaintingType crudPaintingType;

    public PaintingTypeServiceImpl(CrudPaintingType crudPaintingType) {
        this.crudPaintingType = crudPaintingType;
    }

    @Override
    public List<DPaintingType> findAllActiveAndCurrentOne(Long id) {
        return crudPaintingType.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DPaintingType find(Long id) {
        return crudPaintingType.find(id);
    }

    @Override
    public DPaintingType insert(DPaintingType domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudPaintingType.insert(domain);
    }

    @Override
    public DPaintingType update(Long id, DPaintingType domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudPaintingType.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudPaintingType.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudPaintingType.delete(id);
    }

    private void validateDuplicatedResource(DPaintingType domain){
        if(crudPaintingType.findByDescription(domain.getDescription())
                .stream()
                .anyMatch(t -> !t.getId().equals(Optional.ofNullable(domain.getId()).orElse(-1L)))){
            throw new DuplicatedResourceException("Verifique o campo descrição.");
        }
    }
}
