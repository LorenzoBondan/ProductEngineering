package br.com.todeschini.lixeiraservicedomain.lixeira.spi;

import br.com.todeschini.lixeiraservicedomain.lixeira.DLixeira;
import br.com.todeschini.libvalidationhandler.pageable.PageableRequest;
import br.com.todeschini.libvalidationhandler.pageable.Paged;

import java.util.Map;

public interface LixeiraOperations {

    Paged<DLixeira> buscar(PageableRequest request);
    <T> void incluir(T entity) throws IllegalAccessException;
    <T> void recuperar(Integer id, Boolean recuperarDependencias) throws IllegalAccessException;
    <T> void recuperarPorEntidadeId(Map<String, Object> entidadeid, Boolean recuperarDependencias) throws IllegalAccessException;
    <T> void remover(T entity) throws IllegalAccessException;
}
