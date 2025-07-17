package br.com.todeschini.itemservicepersistence.publico.modelo;

import br.com.todeschini.itemservicepersistence.entities.Modelo;
import br.com.todeschini.libspecificationhandler.SearchCriteria;
import br.com.todeschini.libspecificationhandler.SearchSpecificationImpl;

public class ModeloSpecification extends SearchSpecificationImpl<Modelo> {

    public ModeloSpecification(SearchCriteria criteria) {
        super(criteria);
    }
}
