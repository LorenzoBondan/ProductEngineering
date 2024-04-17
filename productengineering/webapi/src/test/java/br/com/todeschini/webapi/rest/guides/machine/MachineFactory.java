package br.com.todeschini.webapi.rest.guides.machine;

import br.com.todeschini.domain.business.guides.machine.DMachine;
import br.com.todeschini.domain.business.guides.machinegroup.DMachineGroup;

public class MachineFactory {

    public static DMachine createDMachine() {
        DMachine Machine = new DMachine();
        Machine.setId(1L);
        Machine.setName("Machine");
        Machine.setMachineGroupId(1L);
        return Machine;
    }

    public static DMachine createDuplicatedDMachine() {
        DMachine Machine = new DMachine();
        Machine.setId(2L);
        Machine.setName("Machine");
        Machine.setMachineGroupId(1L);
        return Machine;
    }

    public static DMachine createNonExistingDMachine(Long nonExistingId) {
        DMachine Machine = new DMachine();
        Machine.setId(nonExistingId);
        Machine.setName("Machine");
        Machine.setMachineGroupId(1L);
        return Machine;
    }
}
