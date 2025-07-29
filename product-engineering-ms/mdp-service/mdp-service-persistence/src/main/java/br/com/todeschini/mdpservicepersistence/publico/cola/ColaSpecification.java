package br.com.todeschini.mdpservicepersistence.publico.cola;

import br.com.todeschini.libspecificationhandler.SearchCriteria;
import br.com.todeschini.libspecificationhandler.SearchSpecificationImpl;
import br.com.todeschini.mdpservicepersistence.entities.Cola;

public class ColaSpecification extends SearchSpecificationImpl<Cola> {

    public ColaSpecification(SearchCriteria criteria) {
        super(criteria);
    }
}
