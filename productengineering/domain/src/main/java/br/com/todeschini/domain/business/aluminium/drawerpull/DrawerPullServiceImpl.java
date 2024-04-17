package br.com.todeschini.domain.business.aluminium.drawerpull;

import br.com.todeschini.domain.business.aluminium.drawerpull.api.DrawerPullService;
import br.com.todeschini.domain.business.aluminium.drawerpull.spi.CrudDrawerPull;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class DrawerPullServiceImpl implements DrawerPullService {

    private final CrudDrawerPull crudDrawerPull;

    public DrawerPullServiceImpl(CrudDrawerPull crudDrawerPull) {
        this.crudDrawerPull = crudDrawerPull;
    }

    @Override
    public List<DDrawerPull> findAllActiveAndCurrentOne(Long id) {
        return crudDrawerPull.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DDrawerPull find(Long id) {
        return crudDrawerPull.find(id);
    }

    @Override
    public DDrawerPull insert(DDrawerPull domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudDrawerPull.insert(domain);
    }

    @Override
    public DDrawerPull update(Long id, DDrawerPull domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudDrawerPull.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudDrawerPull.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudDrawerPull.delete(id);
    }

    private void validateDuplicatedResource(DDrawerPull domain){
        if(crudDrawerPull.findByDescription(domain.getDescription())
                .stream()
                .anyMatch(t -> !t.getCode().equals(Optional.ofNullable(domain.getCode()).orElse(-1L)))){
            throw new DuplicatedResourceException("Verifique o campo descrição.");
        }
    }
}
