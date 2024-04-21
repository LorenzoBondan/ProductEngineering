package br.com.todeschini.domain.business.mdf.painting;

import br.com.todeschini.domain.business.mdf.painting.api.PaintingService;
import br.com.todeschini.domain.business.mdf.painting.spi.CrudPainting;
import br.com.todeschini.domain.exceptions.UniqueConstraintViolationException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class PaintingServiceImpl implements PaintingService {

    private final CrudPainting crudPainting;

    public PaintingServiceImpl(CrudPainting crudPainting) {
        this.crudPainting = crudPainting;
    }

    @Override
    public List<DPainting> findAllActiveAndCurrentOne(Long id) {
        return crudPainting.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DPainting find(Long id) {
        return crudPainting.find(id);
    }

    @Override
    public DPainting insert(DPainting domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudPainting.insert(domain);
    }

    @Override
    public DPainting update(Long id, DPainting domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudPainting.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudPainting.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudPainting.delete(id);
    }

    private void validateDuplicatedResource(DPainting domain){
        if(crudPainting.findByColorAndPaintingType(domain.getColor().getCode(), domain.getPaintingType().getId())
                .stream()
                .anyMatch(t -> !t.getCode().equals(Optional.ofNullable(domain.getCode()).orElse(-1L)))){
            String detailedMessage = "Registro duplicado para a combinação de Cor e Tipo de Pintura.";
            throw new UniqueConstraintViolationException("chk_painting_type_uk", detailedMessage);
        }
    }
}
