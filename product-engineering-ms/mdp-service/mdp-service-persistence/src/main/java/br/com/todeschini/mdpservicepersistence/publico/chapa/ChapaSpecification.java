package br.com.todeschini.mdpservicepersistence.publico.chapa;

import br.com.todeschini.libspecificationhandler.SearchCriteria;
import br.com.todeschini.libspecificationhandler.SearchSpecificationImpl;
import br.com.todeschini.mdpservicepersistence.entities.Chapa;

public class ChapaSpecification extends SearchSpecificationImpl<Chapa> {

    public ChapaSpecification(SearchCriteria criteria) {
        super(criteria);
    }
}
