package br.com.todeschini.domain.business.packaging.plastic;

import br.com.todeschini.domain.business.packaging.plastic.api.PlasticService;
import br.com.todeschini.domain.business.packaging.plastic.spi.CrudPlastic;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class PlasticServiceImpl implements PlasticService {

    private final CrudPlastic crudPlastic;

    public PlasticServiceImpl(CrudPlastic crudPlastic) {
        this.crudPlastic = crudPlastic;
    }

    @Override
    public List<DPlastic> findAllActiveAndCurrentOne(Long id) {
        return crudPlastic.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DPlastic find(Long id) {
        return crudPlastic.find(id);
    }

    @Override
    public DPlastic insert(DPlastic domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudPlastic.insert(domain);
    }

    @Override
    public DPlastic update(Long id, DPlastic domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudPlastic.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudPlastic.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudPlastic.delete(id);
    }

    private void validateDuplicatedResource(DPlastic domain){
        if(crudPlastic.findByDescription(domain.getDescription())
                .stream()
                .anyMatch(t -> !t.getCode().equals(Optional.ofNullable(domain.getCode()).orElse(-1L)))){
            throw new DuplicatedResourceException("Verifique o campo descrição.");
        }
    }
}
