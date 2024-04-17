package br.com.todeschini.webapi.rest.publico.material;

import br.com.todeschini.domain.business.publico.material.DMaterial;

public class MaterialFactory {

    public static DMaterial createDMaterial() {
        DMaterial material = new DMaterial();
        material.setId(1L);
        material.setName("Material");
        return material;
    }

    public static DMaterial createDuplicatedDMaterial() {
        DMaterial material = new DMaterial();
        material.setId(2L);
        material.setName("Material");
        return material;
    }

    public static DMaterial createNonExistingDMaterial(Long nonExistingId) {
        DMaterial material = new DMaterial();
        material.setId(nonExistingId);
        material.setName("Material");
        return material;
    }
}
