package br.com.todeschini.domain.business.mdp.mdpson;

import br.com.todeschini.domain.business.mdp.mdpson.api.MDPSonService;
import br.com.todeschini.domain.business.mdp.mdpson.spi.CrudMDPSon;
import br.com.todeschini.domain.business.publico.son.DSon;
import br.com.todeschini.domain.business.publico.son.api.SonService;
import br.com.todeschini.domain.business.publico.son.spi.CrudSon;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class MDPSonServiceImpl implements MDPSonService {

    private final CrudMDPSon crudMDPSon;

    public MDPSonServiceImpl(CrudMDPSon crudMDPSon) {
        this.crudMDPSon = crudMDPSon;
    }

    @Override
    public List<DMDPSon> findAllActiveAndCurrentOne(Long id) {
        return crudMDPSon.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DMDPSon find(Long id) {
        return crudMDPSon.find(id);
    }

    @Override
    public DMDPSon insert(DMDPSon domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudMDPSon.insert(domain);
    }

    @Override
    public DMDPSon update(Long id, DMDPSon domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudMDPSon.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudMDPSon.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudMDPSon.delete(id);
    }

    private void validateDuplicatedResource(DMDPSon domain){
        if(crudMDPSon.findByDescription(domain.getDescription())
                .stream()
                .anyMatch(t -> !t.getCode().equals(Optional.ofNullable(domain.getCode()).orElse(-1L)))){
            throw new DuplicatedResourceException("Verifique o campo descrição.");
        }
    }
}
