package br.com.todeschini.itemservicepersistence.publico.filho;

import br.com.todeschini.itemservicepersistence.entities.Filho;
import br.com.todeschini.libspecificationhandler.SearchCriteria;
import br.com.todeschini.libspecificationhandler.SearchSpecificationImpl;

public class FilhoSpecification extends SearchSpecificationImpl<Filho> {

    public FilhoSpecification(SearchCriteria criteria) {
        super(criteria);
    }
}
