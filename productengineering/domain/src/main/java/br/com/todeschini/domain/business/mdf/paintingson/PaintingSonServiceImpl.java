package br.com.todeschini.domain.business.mdf.paintingson;

import br.com.todeschini.domain.business.mdf.paintingson.api.PaintingSonService;
import br.com.todeschini.domain.business.mdf.paintingson.spi.CrudPaintingSon;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class PaintingSonServiceImpl implements PaintingSonService {

    private final CrudPaintingSon crudPaintingSon;

    public PaintingSonServiceImpl(CrudPaintingSon crudPaintingSon) {
        this.crudPaintingSon = crudPaintingSon;
    }

    @Override
    public List<DPaintingSon> findAllActiveAndCurrentOne(Long id) {
        return crudPaintingSon.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DPaintingSon find(Long id) {
        return crudPaintingSon.find(id);
    }

    @Override
    public DPaintingSon insert(DPaintingSon domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudPaintingSon.insert(domain);
    }

    @Override
    public DPaintingSon update(Long id, DPaintingSon domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudPaintingSon.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudPaintingSon.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudPaintingSon.delete(id);
    }

    private void validateDuplicatedResource(DPaintingSon domain){
        if(crudPaintingSon.findByDescription(domain.getDescription())
                .stream()
                .anyMatch(t -> !t.getCode().equals(Optional.ofNullable(domain.getCode()).orElse(-1L)))){
            throw new DuplicatedResourceException("Verifique o campo descrição.");
        }
    }
}
