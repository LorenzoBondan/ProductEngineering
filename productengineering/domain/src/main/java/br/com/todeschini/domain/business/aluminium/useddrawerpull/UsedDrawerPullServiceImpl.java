package br.com.todeschini.domain.business.aluminium.useddrawerpull;

import br.com.todeschini.domain.business.aluminium.useddrawerpull.api.UsedDrawerPullService;
import br.com.todeschini.domain.business.aluminium.useddrawerpull.spi.CrudUsedDrawerPull;
import br.com.todeschini.domain.exceptions.UniqueConstraintViolationException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class UsedDrawerPullServiceImpl implements UsedDrawerPullService {

    private final CrudUsedDrawerPull crudUsedDrawerPull;

    public UsedDrawerPullServiceImpl(CrudUsedDrawerPull crudUsedDrawerPull) {
        this.crudUsedDrawerPull = crudUsedDrawerPull;
    }

    @Override
    public List<DUsedDrawerPull> findAllActiveAndCurrentOne(Long id) {
        return crudUsedDrawerPull.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DUsedDrawerPull find(Long id) {
        return crudUsedDrawerPull.find(id);
    }

    @Override
    public DUsedDrawerPull insert(DUsedDrawerPull domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudUsedDrawerPull.insert(domain);
    }

    @Override
    public DUsedDrawerPull update(Long id, DUsedDrawerPull domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudUsedDrawerPull.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudUsedDrawerPull.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudUsedDrawerPull.delete(id);
    }

    private void validateDuplicatedResource(DUsedDrawerPull domain){
        if(crudUsedDrawerPull.findByDrawerPullAndAluminiumSon(domain.getDrawerPullCode(), domain.getAluminiumSonCode())
                .stream()
                .anyMatch(t -> !t.getId().equals(Optional.ofNullable(domain.getId()).orElse(-1L)))){
            String detailedMessage = "Registro duplicado para a combinação de Puxador e Filho.";
            throw new UniqueConstraintViolationException("chk_used_drawer_pull_unq", detailedMessage);
        }
    }
}
