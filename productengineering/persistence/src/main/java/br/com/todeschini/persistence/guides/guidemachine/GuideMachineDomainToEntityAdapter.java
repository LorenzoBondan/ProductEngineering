package br.com.todeschini.persistence.guides.guidemachine;

import br.com.todeschini.domain.business.guides.guidemachine.DGuideMachine;
import br.com.todeschini.persistence.entities.guides.GuideMachine;
import br.com.todeschini.persistence.guides.guide.GuideRepository;
import br.com.todeschini.persistence.guides.machine.MachineRepository;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class GuideMachineDomainToEntityAdapter implements Convertable<GuideMachine, DGuideMachine> {

    private final MachineRepository machineRepository;
    private final GuideRepository guideRepository;

    public GuideMachineDomainToEntityAdapter(GuideRepository guideRepository, MachineRepository machineRepository) {
        this.guideRepository = guideRepository;
        this.machineRepository = machineRepository;
    }

    @Override
    public GuideMachine toEntity(DGuideMachine domain) {
        return GuideMachine.builder()
                .id(domain.getId())
                .guide(guideRepository.findById(domain.getGuideId()).get())
                .machine(machineRepository.findById(domain.getMachineId()).get())
                .machineTime(domain.getMachineTime())
                .manTime(domain.getManTime())
                .build();
    }

    @Override
    public DGuideMachine toDomain(GuideMachine entity) {
        return DGuideMachine.builder()
                .id(entity.getId())
                .guideId(entity.getMachine().getId())
                .machineId(entity.getMachine().getId())
                .machineTime(entity.getMachineTime())
                .manTime(entity.getManTime())
                .measurementUnit(entity.getMeasurementUnit())
                .build();
    }
}
