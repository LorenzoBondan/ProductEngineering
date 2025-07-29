package br.com.todeschini.libvalidationhandler.contracts;

import br.com.todeschini.libvalidationhandler.pageable.PageableRequest;
import br.com.todeschini.libvalidationhandler.pageable.Paged;

/**
 * CRUDs standard contracts.
 * @param <T> entity
 * @param <J> entity's PK
 */
public interface SimpleCrud<T, J>{

    Paged<T> buscar(PageableRequest request);

    T buscar(J obj);

    T incluir(T obj);

    T atualizar(T obj);

    void inativar(J obj);

    void excluir(J obj) throws IllegalAccessException;
}
