package br.com.todeschini.domain.business.publico.material;

import br.com.todeschini.domain.business.publico.material.api.MaterialService;
import br.com.todeschini.domain.business.publico.material.spi.CrudMaterial;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class MaterialServiceImpl implements MaterialService {

    private final CrudMaterial crudMaterial;

    public MaterialServiceImpl(CrudMaterial crudMaterial) {
        this.crudMaterial = crudMaterial;
    }

    @Override
    public List<DMaterial> findAllActiveAndCurrentOne(Long id) {
        return crudMaterial.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DMaterial find(Long id) {
        return crudMaterial.find(id);
    }

    @Override
    public DMaterial insert(DMaterial domain) {
        validateDuplicatedResource(domain);
        domain.validate();
        return crudMaterial.insert(domain);
    }

    @Override
    public DMaterial update(Long id, DMaterial domain) {
        validateDuplicatedResource(domain);
        domain.validate();
        return crudMaterial.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudMaterial.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudMaterial.delete(id);
    }

    private void validateDuplicatedResource(DMaterial domain){
        if(crudMaterial.findByName(domain.getName())
                .stream()
                .anyMatch(t -> !t.getId().equals(Optional.ofNullable(domain.getId()).orElse(-1L)))){
            throw new DuplicatedResourceException("Verifique o campo nome.");
        }
    }
}
