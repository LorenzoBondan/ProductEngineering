package br.com.todeschini.webapi.rest.publico.materialusado;

import br.com.todeschini.domain.business.publico.filho.DFilho;
import br.com.todeschini.domain.business.publico.material.DMaterial;
import br.com.todeschini.domain.business.publico.materialusado.DMaterialUsado;

public class MaterialUsadoFactory {

    public static DMaterialUsado createDMaterialUsado() {
        DMaterialUsado materialUsado = new DMaterialUsado();
        materialUsado.setCodigo(1);
        materialUsado.setFilho(new DFilho(1));
        materialUsado.setMaterial(new DMaterial(2));
        materialUsado.setQuantidadeLiquida(1.00);
        materialUsado.setQuantidadeBruta(1.00);
        materialUsado.setValor(1.00);
        materialUsado.setUnidadeMedida("KG");
        return materialUsado;
    }

    public static DMaterialUsado createDuplicatedDMaterialUsado(Integer duplicatedId) {
        DMaterialUsado materialUsado = new DMaterialUsado();
        materialUsado.setCodigo(2);
        materialUsado.setFilho(new DFilho(duplicatedId));
        materialUsado.setMaterial(new DMaterial(duplicatedId));
        materialUsado.setQuantidadeLiquida(1.00);
        materialUsado.setQuantidadeBruta(1.00);
        materialUsado.setValor(1.00);
        materialUsado.setUnidadeMedida("KG");
        return materialUsado;
    }

    public static DMaterialUsado createNonExistingDMaterialUsado(Integer nonExistingId) {
        DMaterialUsado materialUsado = new DMaterialUsado();
        materialUsado.setCodigo(nonExistingId);
        materialUsado.setFilho(new DFilho(1));
        materialUsado.setMaterial(new DMaterial(1));
        materialUsado.setQuantidadeLiquida(1.00);
        materialUsado.setQuantidadeBruta(1.00);
        materialUsado.setValor(1.00);
        materialUsado.setUnidadeMedida("KG");
        return materialUsado;
    }
}
