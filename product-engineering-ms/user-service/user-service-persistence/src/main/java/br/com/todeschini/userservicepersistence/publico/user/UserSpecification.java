package br.com.todeschini.userservicepersistence.publico.user;

import br.com.todeschini.libspecificationhandler.SearchCriteria;
import br.com.todeschini.libspecificationhandler.SearchSpecificationImpl;
import br.com.todeschini.userservicepersistence.entities.User;

public class UserSpecification extends SearchSpecificationImpl<User> {

    public UserSpecification(SearchCriteria criteria) {
        super(criteria);
    }
}
