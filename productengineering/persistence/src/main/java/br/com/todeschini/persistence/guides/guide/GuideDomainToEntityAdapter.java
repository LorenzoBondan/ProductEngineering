package br.com.todeschini.persistence.guides.guide;

import br.com.todeschini.domain.business.guides.guide.DGuide;
import br.com.todeschini.persistence.entities.guides.Guide;
import br.com.todeschini.persistence.guides.guidemachine.GuideMachineDomainToEntityAdapter;
import br.com.todeschini.persistence.guides.guidemachine.GuideMachineRepository;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class GuideDomainToEntityAdapter implements Convertable<Guide, DGuide> {

    private final GuideMachineRepository guideMachineRepository;
    private final GuideMachineDomainToEntityAdapter guideMachineAdapter;

    public GuideDomainToEntityAdapter(GuideMachineRepository guideMachineRepository, GuideMachineDomainToEntityAdapter guideMachineAdapter) {
        this.guideMachineRepository = guideMachineRepository;
        this.guideMachineAdapter = guideMachineAdapter;
    }

    @Override
    public Guide toEntity(DGuide domain) {
        return Guide.builder()
                .id(domain.getId())
                .finalDate(domain.getFinalDate())
                .implementation(domain.getImplementation())

                .guideMachines(domain.getGuideMachines().stream().map(guideMachine -> guideMachineRepository.findById(guideMachine.getId()).get()).collect(Collectors.toSet()))

                .build();
    }

    @Override
    public DGuide toDomain(Guide entity) {
        return DGuide.builder()
                .id(entity.getId())
                .finalDate(entity.getFinalDate())
                .implementation(entity.getImplementation())

                .guideMachines(entity.getGuideMachines().stream().map(guideMachineAdapter::toDomain).collect(Collectors.toList()))
                .value(Math.round(entity.calculateValue() * 1e2) / 1e2)
                .build();
    }
}
