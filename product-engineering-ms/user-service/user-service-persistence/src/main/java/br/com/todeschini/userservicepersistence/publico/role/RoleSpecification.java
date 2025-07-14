package br.com.todeschini.userservicepersistence.publico.role;

import br.com.todeschini.libspecificationhandler.SearchCriteria;
import br.com.todeschini.libspecificationhandler.SearchSpecificationImpl;
import br.com.todeschini.userservicepersistence.entities.Role;

public class RoleSpecification extends SearchSpecificationImpl<Role> {

    public RoleSpecification(SearchCriteria criteria) {
        super(criteria);
    }
}
