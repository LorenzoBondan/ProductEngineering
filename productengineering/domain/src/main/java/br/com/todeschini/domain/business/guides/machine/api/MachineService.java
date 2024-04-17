package br.com.todeschini.domain.business.guides.machine.api;

import org.springframework.stereotype.Component;

@Component
public interface MachineService extends FindMachine, InsertMachine, UpdateMachine, DeleteMachine, InactivateMachine,
        FindAllActiveMachineAndCurrentOne {
}
