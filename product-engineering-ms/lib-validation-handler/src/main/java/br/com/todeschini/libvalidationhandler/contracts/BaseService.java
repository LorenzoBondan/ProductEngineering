package br.com.todeschini.libvalidationhandler.contracts;

import br.com.todeschini.libvalidationhandler.pageable.PageableRequest;
import br.com.todeschini.libvalidationhandler.pageable.Paged;

/**
 * Services standard contracts.
 * @param <T> entity
 */
public interface BaseService<T>{

    Paged<T> findAll(PageableRequest request);

    T find(Integer id);

    T create(T obj);

    T update(T obj);

    void delete(Integer id);
}
