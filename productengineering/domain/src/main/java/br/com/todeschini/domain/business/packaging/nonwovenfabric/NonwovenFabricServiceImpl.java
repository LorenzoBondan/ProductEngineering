package br.com.todeschini.domain.business.packaging.nonwovenfabric;

import br.com.todeschini.domain.business.packaging.nonwovenfabric.api.NonwovenFabricService;
import br.com.todeschini.domain.business.packaging.nonwovenfabric.spi.CrudNonwovenFabric;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class NonwovenFabricServiceImpl implements NonwovenFabricService {

    private final CrudNonwovenFabric crudNonwovenFabric;

    public NonwovenFabricServiceImpl(CrudNonwovenFabric crudNonwovenFabric) {
        this.crudNonwovenFabric = crudNonwovenFabric;
    }

    @Override
    public List<DNonwovenFabric> findAllActiveAndCurrentOne(Long id) {
        return crudNonwovenFabric.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DNonwovenFabric find(Long id) {
        return crudNonwovenFabric.find(id);
    }

    @Override
    public DNonwovenFabric insert(DNonwovenFabric domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudNonwovenFabric.insert(domain);
    }

    @Override
    public DNonwovenFabric update(Long id, DNonwovenFabric domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudNonwovenFabric.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudNonwovenFabric.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudNonwovenFabric.delete(id);
    }

    private void validateDuplicatedResource(DNonwovenFabric domain){
        if(crudNonwovenFabric.findByDescription(domain.getDescription())
                .stream()
                .anyMatch(t -> !t.getCode().equals(Optional.ofNullable(domain.getCode()).orElse(-1L)))){
            throw new DuplicatedResourceException("Verifique o campo descrição.");
        }
    }
}
