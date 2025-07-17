package br.com.todeschini.itemservicepersistence.publico.materialusado;

import br.com.todeschini.itemservicepersistence.entities.MaterialUsado;
import br.com.todeschini.libspecificationhandler.SearchCriteria;
import br.com.todeschini.libspecificationhandler.SearchSpecificationImpl;

public class MaterialUsadoSpecification extends SearchSpecificationImpl<MaterialUsado> {

    public MaterialUsadoSpecification(SearchCriteria criteria) {
        super(criteria);
    }
}
