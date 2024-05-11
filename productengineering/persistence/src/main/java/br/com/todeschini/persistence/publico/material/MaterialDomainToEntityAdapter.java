package br.com.todeschini.persistence.publico.material;

import br.com.todeschini.domain.business.publico.material.DMaterial;
import br.com.todeschini.persistence.entities.publico.Material;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class MaterialDomainToEntityAdapter implements Convertable<Material, DMaterial> {

    @Override
    public Material toEntity(DMaterial domain) {
        return Material.builder()
                .id(domain.getId())
                .name(domain.getName().toUpperCase())
                .build();
    }

    @Override
    public DMaterial toDomain(Material entity) {
        return DMaterial.builder()
                .id(entity.getId())
                .name(entity.getName().toUpperCase())
                .build();
    }
}
