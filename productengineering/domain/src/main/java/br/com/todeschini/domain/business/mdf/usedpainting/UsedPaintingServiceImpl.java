package br.com.todeschini.domain.business.mdf.usedpainting;

import br.com.todeschini.domain.business.mdf.usedpainting.api.UsedPaintingService;
import br.com.todeschini.domain.business.mdf.usedpainting.spi.CrudUsedPainting;
import br.com.todeschini.domain.exceptions.UniqueConstraintViolationException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class UsedPaintingServiceImpl implements UsedPaintingService {

    private final CrudUsedPainting crudUsedPainting;

    public UsedPaintingServiceImpl(CrudUsedPainting crudUsedPainting) {
        this.crudUsedPainting = crudUsedPainting;
    }

    @Override
    public List<DUsedPainting> findAllActiveAndCurrentOne(Long id) {
        return crudUsedPainting.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DUsedPainting find(Long id) {
        return crudUsedPainting.find(id);
    }

    @Override
    public DUsedPainting insert(DUsedPainting domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudUsedPainting.insert(domain);
    }

    @Override
    public DUsedPainting update(Long id, DUsedPainting domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudUsedPainting.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudUsedPainting.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudUsedPainting.delete(id);
    }

    private void validateDuplicatedResource(DUsedPainting domain){
        if(crudUsedPainting.findByPaintingAndPaintingSon(domain.getPaintingCode(), domain.getPaintingSonCode())
                .stream()
                .anyMatch(t -> !t.getId().equals(Optional.ofNullable(domain.getId()).orElse(-1L)))){
            throw new UniqueConstraintViolationException("Registro duplicado para a combinação de Pintura e Filho.");
        }
    }
}
