package br.com.todeschini.domain.business.mdp.edgebanding;

import br.com.todeschini.domain.business.mdp.edgebanding.api.EdgeBandingService;
import br.com.todeschini.domain.business.mdp.edgebanding.spi.CrudEdgeBanding;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class EdgeBandingServiceImpl implements EdgeBandingService {

    private final CrudEdgeBanding crudEdgeBanding;

    public EdgeBandingServiceImpl(CrudEdgeBanding crudEdgeBanding) {
        this.crudEdgeBanding = crudEdgeBanding;
    }

    @Override
    public List<DEdgeBanding> findAllActiveAndCurrentOne(Long id) {
        return crudEdgeBanding.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DEdgeBanding find(Long id) {
        return crudEdgeBanding.find(id);
    }

    @Override
    public DEdgeBanding insert(DEdgeBanding domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudEdgeBanding.insert(domain);
    }

    @Override
    public DEdgeBanding update(Long id, DEdgeBanding domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudEdgeBanding.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudEdgeBanding.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudEdgeBanding.delete(id);
    }

    private void validateDuplicatedResource(DEdgeBanding domain){
        if(crudEdgeBanding.findByDescription(domain.getDescription())
                .stream()
                .anyMatch(t -> !t.getCode().equals(Optional.ofNullable(domain.getCode()).orElse(-1L)))){
            throw new DuplicatedResourceException("Verifique o campo descrição.");
        }
    }
}
