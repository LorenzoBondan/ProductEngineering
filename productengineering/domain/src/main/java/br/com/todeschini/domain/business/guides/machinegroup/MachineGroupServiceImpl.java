package br.com.todeschini.domain.business.guides.machinegroup;

import br.com.todeschini.domain.business.guides.machinegroup.api.MachineGroupService;
import br.com.todeschini.domain.business.guides.machinegroup.spi.CrudMachineGroup;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class MachineGroupServiceImpl implements MachineGroupService {

    private final CrudMachineGroup crudMachineGroup;

    public MachineGroupServiceImpl(CrudMachineGroup crudMachineGroup) {
        this.crudMachineGroup = crudMachineGroup;
    }

    @Override
    public List<DMachineGroup> findAllActiveAndCurrentOne(Long id) {
        return crudMachineGroup.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DMachineGroup find(Long id) {
        return crudMachineGroup.find(id);
    }

    @Override
    public DMachineGroup insert(DMachineGroup domain) {
        validateDuplicatedResource(domain);
        domain.validate();
        return crudMachineGroup.insert(domain);
    }

    @Override
    public DMachineGroup update(Long id, DMachineGroup domain) {
        validateDuplicatedResource(domain);
        domain.validate();
        return crudMachineGroup.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudMachineGroup.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudMachineGroup.delete(id);
    }

    private void validateDuplicatedResource(DMachineGroup domain){
        if(crudMachineGroup.findByName(domain.getName())
                .stream()
                .anyMatch(t -> !t.getId().equals(Optional.ofNullable(domain.getId()).orElse(-1L)))){
            throw new DuplicatedResourceException("Verifique o campo nome.");
        }
    }
}
