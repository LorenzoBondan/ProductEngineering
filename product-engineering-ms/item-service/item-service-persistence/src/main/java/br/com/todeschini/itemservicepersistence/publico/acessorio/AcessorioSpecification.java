package br.com.todeschini.itemservicepersistence.publico.acessorio;

import br.com.todeschini.itemservicepersistence.entities.Acessorio;
import br.com.todeschini.libspecificationhandler.SearchCriteria;
import br.com.todeschini.libspecificationhandler.SearchSpecificationImpl;

public class AcessorioSpecification extends SearchSpecificationImpl<Acessorio> {

    public AcessorioSpecification(SearchCriteria criteria) {
        super(criteria);
    }
}
