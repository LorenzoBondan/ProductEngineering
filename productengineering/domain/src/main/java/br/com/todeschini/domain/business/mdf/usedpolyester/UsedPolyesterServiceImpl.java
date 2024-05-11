package br.com.todeschini.domain.business.mdf.usedpolyester;

import br.com.todeschini.domain.business.mdf.usedpolyester.api.UsedPolyesterService;
import br.com.todeschini.domain.business.mdf.usedpolyester.spi.CrudUsedPolyester;
import br.com.todeschini.domain.exceptions.UniqueConstraintViolationException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class UsedPolyesterServiceImpl implements UsedPolyesterService {

    private final CrudUsedPolyester crudUsedPolyester;

    public UsedPolyesterServiceImpl(CrudUsedPolyester crudUsedPolyester) {
        this.crudUsedPolyester = crudUsedPolyester;
    }

    @Override
    public List<DUsedPolyester> findAllActiveAndCurrentOne(Long id) {
        return crudUsedPolyester.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DUsedPolyester find(Long id) {
        return crudUsedPolyester.find(id);
    }

    @Override
    public DUsedPolyester insert(DUsedPolyester domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudUsedPolyester.insert(domain);
    }

    @Override
    public DUsedPolyester update(Long id, DUsedPolyester domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudUsedPolyester.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudUsedPolyester.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudUsedPolyester.delete(id);
    }

    private void validateDuplicatedResource(DUsedPolyester domain){
        if(crudUsedPolyester.findByPolyesterAndPaintingSon(domain.getPolyesterCode(), domain.getPolyesterCode())
                .stream()
                .anyMatch(t -> !t.getId().equals(Optional.ofNullable(domain.getId()).orElse(-1L)))){
            throw new UniqueConstraintViolationException("Registro duplicado para a combinação de Poliéster e Filho.");
        }
    }
}
