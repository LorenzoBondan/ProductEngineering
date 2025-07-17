package br.com.todeschini.itemservicepersistence.publico.material;

import br.com.todeschini.itemservicepersistence.entities.Material;
import br.com.todeschini.libspecificationhandler.SearchCriteria;
import br.com.todeschini.libspecificationhandler.SearchSpecificationImpl;

public class MaterialSpecification extends SearchSpecificationImpl<Material> {

    public MaterialSpecification(SearchCriteria criteria) {
        super(criteria);
    }
}
