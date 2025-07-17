package br.com.todeschini.itemservicepersistence.publico.acessoriousado;

import br.com.todeschini.itemservicepersistence.entities.AcessorioUsado;
import br.com.todeschini.libspecificationhandler.SearchCriteria;
import br.com.todeschini.libspecificationhandler.SearchSpecificationImpl;

public class AcessorioUsadoSpecification extends SearchSpecificationImpl<AcessorioUsado> {

    public AcessorioUsadoSpecification(SearchCriteria criteria) {
        super(criteria);
    }
}
