package br.com.todeschini.domain.business.guides.guidemachine.api;

import org.springframework.stereotype.Component;

@Component
public interface GuideMachineService extends FindGuideMachine, InsertGuideMachine, UpdateGuideMachine, DeleteGuideMachine, InactivateGuideMachine,
        FindAllActiveGuideMachineAndCurrentOne {
}
