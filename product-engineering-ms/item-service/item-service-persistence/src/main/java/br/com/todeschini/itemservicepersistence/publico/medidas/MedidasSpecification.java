package br.com.todeschini.itemservicepersistence.publico.medidas;

import br.com.todeschini.itemservicepersistence.entities.Medidas;
import br.com.todeschini.libspecificationhandler.SearchCriteria;
import br.com.todeschini.libspecificationhandler.SearchSpecificationImpl;

public class MedidasSpecification extends SearchSpecificationImpl<Medidas> {

    public MedidasSpecification(SearchCriteria criteria) {
        super(criteria);
    }
}
