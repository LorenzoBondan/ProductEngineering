package br.com.todeschini.itemservicepersistence.publico.cor;

import br.com.todeschini.itemservicepersistence.entities.Cor;
import br.com.todeschini.libspecificationhandler.SearchCriteria;
import br.com.todeschini.libspecificationhandler.SearchSpecificationImpl;

public class CorSpecification extends SearchSpecificationImpl<Cor> {

    public CorSpecification(SearchCriteria criteria) {
        super(criteria);
    }
}
