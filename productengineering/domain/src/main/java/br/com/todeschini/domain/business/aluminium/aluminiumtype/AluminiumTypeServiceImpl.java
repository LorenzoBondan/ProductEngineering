package br.com.todeschini.domain.business.aluminium.aluminiumtype;

import br.com.todeschini.domain.business.aluminium.aluminiumtype.api.AluminiumTypeService;
import br.com.todeschini.domain.business.aluminium.aluminiumtype.spi.CrudAluminiumType;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class AluminiumTypeServiceImpl implements AluminiumTypeService {

    private final CrudAluminiumType crudAluminiumType;

    public AluminiumTypeServiceImpl(CrudAluminiumType crudAluminiumType) {
        this.crudAluminiumType = crudAluminiumType;
    }

    @Override
    public List<DAluminiumType> findAllActiveAndCurrentOne(Long id) {
        return crudAluminiumType.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DAluminiumType find(Long id) {
        return crudAluminiumType.find(id);
    }

    @Override
    public DAluminiumType insert(DAluminiumType domain) {
        validateDuplicatedResource(domain);
        domain.validate();
        return crudAluminiumType.insert(domain);
    }

    @Override
    public DAluminiumType update(Long id, DAluminiumType domain) {
        validateDuplicatedResource(domain);
        domain.validate();
        return crudAluminiumType.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudAluminiumType.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudAluminiumType.delete(id);
    }

    private void validateDuplicatedResource(DAluminiumType domain){
        if(crudAluminiumType.findByName(domain.getName())
                .stream()
                .anyMatch(t -> !t.getId().equals(Optional.ofNullable(domain.getId()).orElse(-1L)))){
            throw new DuplicatedResourceException("Verifique o campo nome.");
        }
    }
}
