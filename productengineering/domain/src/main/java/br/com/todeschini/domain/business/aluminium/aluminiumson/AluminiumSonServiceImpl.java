package br.com.todeschini.domain.business.aluminium.aluminiumson;

import br.com.todeschini.domain.business.aluminium.aluminiumson.api.AluminiumSonService;
import br.com.todeschini.domain.business.aluminium.aluminiumson.spi.CrudAluminiumSon;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class AluminiumSonServiceImpl implements AluminiumSonService {

    private final CrudAluminiumSon crudAluminiumSon;

    public AluminiumSonServiceImpl(CrudAluminiumSon crudAluminiumSon) {
        this.crudAluminiumSon = crudAluminiumSon;
    }

    @Override
    public List<DAluminiumSon> findAllActiveAndCurrentOne(Long id) {
        return crudAluminiumSon.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DAluminiumSon find(Long id) {
        return crudAluminiumSon.find(id);
    }

    @Override
    public DAluminiumSon insert(DAluminiumSon domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudAluminiumSon.insert(domain);
    }

    @Override
    public DAluminiumSon update(Long id, DAluminiumSon domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudAluminiumSon.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudAluminiumSon.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudAluminiumSon.delete(id);
    }

    private void validateDuplicatedResource(DAluminiumSon domain){
        if(crudAluminiumSon.findByDescription(domain.getDescription())
                .stream()
                .anyMatch(t -> !t.getCode().equals(Optional.ofNullable(domain.getCode()).orElse(-1L)))){
            throw new DuplicatedResourceException("Verifique o campo descrição.");
        }
    }
}
