package br.com.todeschini.domain.business.guides.guidemachine;

import br.com.todeschini.domain.business.guides.guidemachine.api.GuideMachineService;
import br.com.todeschini.domain.business.guides.guidemachine.spi.CrudGuideMachine;
import br.com.todeschini.domain.exceptions.UniqueConstraintViolationException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class GuideMachineServiceImpl implements GuideMachineService {

    private final CrudGuideMachine crudGuideMachine;

    public GuideMachineServiceImpl(CrudGuideMachine crudGuideMachine) {
        this.crudGuideMachine = crudGuideMachine;
    }

    @Override
    public List<DGuideMachine> findAllActiveAndCurrentOne(Long id) {
        return crudGuideMachine.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DGuideMachine find(Long id) {
        return crudGuideMachine.find(id);
    }

    @Override
    public DGuideMachine insert(DGuideMachine domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudGuideMachine.insert(domain);
    }

    @Override
    public DGuideMachine update(Long id, DGuideMachine domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudGuideMachine.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudGuideMachine.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudGuideMachine.delete(id);
    }

    private void validateDuplicatedResource(DGuideMachine domain){
        if(crudGuideMachine.findByGuideAndMachine(domain.getGuideId(), domain.getMachineId())
                .stream()
                .anyMatch(t -> !t.getId().equals(Optional.ofNullable(domain.getId()).orElse(-1L)))){
            throw new UniqueConstraintViolationException("Registro duplicado para a combinação de Guia e Máquina.");
        }
    }
}
