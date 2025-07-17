package br.com.todeschini.libvalidationhandler.contracts;

import br.com.todeschini.libvalidationhandler.pageable.PageableRequest;
import br.com.todeschini.libvalidationhandler.pageable.Paged;

/**
 * Services standard contracts.
 * @param <T> entity
 */
public interface BaseService<T>{

    Paged<T> buscar(PageableRequest request);

    T buscar(Integer id);

    T incluir(T obj);

    T atualizar(T obj);

    void inativar(Integer id);

    void excluir(Integer id);
}
