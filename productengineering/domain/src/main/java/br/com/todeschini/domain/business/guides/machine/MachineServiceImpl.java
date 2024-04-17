package br.com.todeschini.domain.business.guides.machine;

import br.com.todeschini.domain.business.guides.machine.api.MachineService;
import br.com.todeschini.domain.business.guides.machine.spi.CrudMachine;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class MachineServiceImpl implements MachineService {

    private final CrudMachine crudMachine;

    public MachineServiceImpl(CrudMachine crudMachine) {
        this.crudMachine = crudMachine;
    }

    @Override
    public List<DMachine> findAllActiveAndCurrentOne(Long id) {
        return crudMachine.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DMachine find(Long id) {
        return crudMachine.find(id);
    }

    @Override
    public DMachine insert(DMachine domain) {
        validateDuplicatedResource(domain);
        domain.validate();
        return crudMachine.insert(domain);
    }

    @Override
    public DMachine update(Long id, DMachine domain) {
        validateDuplicatedResource(domain);
        domain.validate();
        return crudMachine.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudMachine.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudMachine.delete(id);
    }

    private void validateDuplicatedResource(DMachine domain){
        if(crudMachine.findByName(domain.getName())
                .stream()
                .anyMatch(t -> !t.getId().equals(Optional.ofNullable(domain.getId()).orElse(-1L)))){
            throw new DuplicatedResourceException("Verifique o campo nome.");
        }
    }
}
