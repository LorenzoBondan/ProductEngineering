package br.com.todeschini.lixeiraservicepersistence.lixeira;

import br.com.todeschini.libspecificationhandler.SearchCriteria;
import br.com.todeschini.libspecificationhandler.SearchSpecificationImpl;
import br.com.todeschini.lixeiraservicepersistence.entities.Lixeira;

public class LixeiraSpecification extends SearchSpecificationImpl<Lixeira> {

    public LixeiraSpecification(SearchCriteria criteria) {
        super(criteria);
    }
}
