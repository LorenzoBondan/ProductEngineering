package br.com.todeschini.domain.business.publico.material.api;

import org.springframework.stereotype.Component;

@Component
public interface MaterialService extends FindMaterial, InsertMaterial, UpdateMaterial, DeleteMaterial, InactivateMaterial,
        FindAllActiveMaterialAndCurrentOne {
}
