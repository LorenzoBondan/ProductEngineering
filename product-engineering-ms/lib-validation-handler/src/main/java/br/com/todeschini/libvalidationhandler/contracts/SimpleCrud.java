package br.com.todeschini.libvalidationhandler.contracts;

import br.com.todeschini.libvalidationhandler.pageable.PageableRequest;
import br.com.todeschini.libvalidationhandler.pageable.Paged;

/**
 * CRUDs standard contracts.
 * @param <T> entity
 * @param <J> entity's PK
 */
public interface SimpleCrud<T, J>{

    Paged<T> findAll(PageableRequest request);

    T find(J obj);

    T insert(T obj);

    T update(T obj);

    void delete(J obj);
}
