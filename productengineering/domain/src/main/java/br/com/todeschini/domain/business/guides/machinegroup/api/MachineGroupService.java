package br.com.todeschini.domain.business.guides.machinegroup.api;

import org.springframework.stereotype.Component;

@Component
public interface MachineGroupService extends FindMachineGroup, InsertMachineGroup, UpdateMachineGroup, DeleteMachineGroup, InactivateMachineGroup,
        FindAllActiveMachineGroupAndCurrentOne {
}
