package br.com.todeschini.itemservicepersistence.publico.pai;

import br.com.todeschini.itemservicepersistence.entities.Pai;
import br.com.todeschini.libspecificationhandler.SearchCriteria;
import br.com.todeschini.libspecificationhandler.SearchSpecificationImpl;

public class PaiSpecification extends SearchSpecificationImpl<Pai> {

    public PaiSpecification(SearchCriteria criteria) {
        super(criteria);
    }
}
