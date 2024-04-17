package br.com.todeschini.persistence.guides.machine;

import br.com.todeschini.domain.business.guides.machine.DMachine;
import br.com.todeschini.persistence.entities.guides.GuideMachine;
import br.com.todeschini.persistence.entities.guides.Machine;
import br.com.todeschini.persistence.guides.guidemachine.GuideMachineRepository;
import br.com.todeschini.persistence.guides.machinegroup.MachineGroupRepository;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MachineDomainToEntityAdapter implements Convertable<Machine, DMachine> {

    private final MachineGroupRepository machineGroupRepository;
    private final GuideMachineRepository guideMachineRepository;

    public MachineDomainToEntityAdapter(MachineGroupRepository machineGroupRepository, GuideMachineRepository guideMachineRepository) {
        this.machineGroupRepository = machineGroupRepository;
        this.guideMachineRepository = guideMachineRepository;
    }

    @Override
    public Machine toEntity(DMachine domain) {
        return Machine.builder()
                .id(domain.getId())
                .name(domain.getName())
                .formula(domain.getFormula())
                .machineGroup(machineGroupRepository.findById(domain.getMachineGroupId()).get())

                .guideMachines(domain.getGuideMachinesIds().stream().map(guideMachineId -> guideMachineRepository.findById(guideMachineId).get()).collect(Collectors.toSet()))

                .build();
    }

    @Override
    public DMachine toDomain(Machine entity) {
        return DMachine.builder()
                .id(entity.getId())
                .name(entity.getName())
                .formula(entity.getFormula())
                .machineGroupId(entity.getMachineGroup().getId())

                .guideMachinesIds(entity.getGuideMachines().stream().map(GuideMachine::getId).collect(Collectors.toList()))

                .build();
    }
}
