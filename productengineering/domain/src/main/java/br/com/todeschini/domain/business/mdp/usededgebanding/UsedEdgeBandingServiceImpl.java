package br.com.todeschini.domain.business.mdp.usededgebanding;

import br.com.todeschini.domain.business.mdp.usededgebanding.api.UsedEdgeBandingService;
import br.com.todeschini.domain.business.mdp.usededgebanding.spi.CrudUsedEdgeBanding;
import br.com.todeschini.domain.exceptions.UniqueConstraintViolationException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class UsedEdgeBandingServiceImpl implements UsedEdgeBandingService {

    private final CrudUsedEdgeBanding crudUsedEdgeBanding;

    public UsedEdgeBandingServiceImpl(CrudUsedEdgeBanding crudUsedEdgeBanding) {
        this.crudUsedEdgeBanding = crudUsedEdgeBanding;
    }

    @Override
    public List<DUsedEdgeBanding> findAllActiveAndCurrentOne(Long id) {
        return crudUsedEdgeBanding.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DUsedEdgeBanding find(Long id) {
        return crudUsedEdgeBanding.find(id);
    }

    @Override
    public DUsedEdgeBanding insert(DUsedEdgeBanding domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudUsedEdgeBanding.insert(domain);
    }

    @Override
    public DUsedEdgeBanding update(Long id, DUsedEdgeBanding domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudUsedEdgeBanding.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudUsedEdgeBanding.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudUsedEdgeBanding.delete(id);
    }

    private void validateDuplicatedResource(DUsedEdgeBanding domain){
        if(crudUsedEdgeBanding.findByEdgeBandingAndMDPSon(domain.getEdgeBandingCode(), domain.getMdpSonCode())
                .stream()
                .anyMatch(t -> !t.getId().equals(Optional.ofNullable(domain.getId()).orElse(-1L)))){
            throw new UniqueConstraintViolationException("Registro duplicado para a combinação de Fita Borda e Filho.");
        }
    }
}
