package br.com.todeschini.webapi.rest.guides.machinegroup;

import br.com.todeschini.domain.business.guides.machinegroup.DMachineGroup;

public class MachineGroupFactory {

    public static DMachineGroup createDMachineGroup() {
        DMachineGroup MachineGroup = new DMachineGroup();
        MachineGroup.setId(1L);
        MachineGroup.setName("MachineGroup");
        return MachineGroup;
    }

    public static DMachineGroup createDuplicatedDMachineGroup() {
        DMachineGroup MachineGroup = new DMachineGroup();
        MachineGroup.setId(2L);
        MachineGroup.setName("MachineGroup");
        return MachineGroup;
    }

    public static DMachineGroup createNonExistingDMachineGroup(Long nonExistingId) {
        DMachineGroup MachineGroup = new DMachineGroup();
        MachineGroup.setId(nonExistingId);
        MachineGroup.setName("MachineGroup");
        return MachineGroup;
    }
}
