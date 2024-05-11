package br.com.todeschini.persistence.guides.machinegroup;

import br.com.todeschini.domain.business.guides.machinegroup.DMachineGroup;
import br.com.todeschini.persistence.entities.guides.MachineGroup;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class MachineGroupDomainToEntityAdapter implements Convertable<MachineGroup, DMachineGroup> {

    @Override
    public MachineGroup toEntity(DMachineGroup domain) {
        return MachineGroup.builder()
                .id(domain.getId())
                .name(domain.getName().toUpperCase())
                .build();
    }

    @Override
    public DMachineGroup toDomain(MachineGroup entity) {
        return DMachineGroup.builder()
                .id(entity.getId())
                .name(entity.getName().toUpperCase())
                .build();
    }
}
