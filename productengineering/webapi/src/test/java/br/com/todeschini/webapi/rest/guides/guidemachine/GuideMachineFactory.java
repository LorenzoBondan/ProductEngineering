package br.com.todeschini.webapi.rest.guides.guidemachine;

import br.com.todeschini.domain.business.guides.guidemachine.DGuideMachine;

public class GuideMachineFactory {

    public static DGuideMachine createDGuideMachine() {
        DGuideMachine GuideMachine = new DGuideMachine();
        GuideMachine.setId(1L);
        GuideMachine.setGuideId(1L);
        GuideMachine.setMachineId(1L);
        GuideMachine.setMachineTime(1.0);
        GuideMachine.setManTime(1.0);
        GuideMachine.setMeasurementUnit("MIN");
        return GuideMachine;
    }

    public static DGuideMachine createDuplicatedDGuideMachine(Long duplicatedId) {
        DGuideMachine GuideMachine = new DGuideMachine();
        GuideMachine.setId(2L);
        GuideMachine.setGuideId(duplicatedId);
        GuideMachine.setMachineId(duplicatedId);
        GuideMachine.setMachineTime(1.0);
        GuideMachine.setManTime(1.0);
        GuideMachine.setMeasurementUnit("MIN");
        return GuideMachine;
    }

    public static DGuideMachine createNonExistingDGuideMachine(Long nonExistingId) {
        DGuideMachine GuideMachine = new DGuideMachine();
        GuideMachine.setId(nonExistingId);
        GuideMachine.setGuideId(1L);
        GuideMachine.setMachineId(1L);
        GuideMachine.setMachineTime(1.0);
        GuideMachine.setManTime(1.0);
        GuideMachine.setMeasurementUnit("MIN");
        return GuideMachine;
    }
}
