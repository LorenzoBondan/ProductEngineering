package br.com.todeschini.domain.business.publico.cola.api;

import br.com.todeschini.domain.business.publico.cola.DCola;

import java.util.List;

public interface BuscarTodosColaAtivosMaisAtual {

    List<DCola> buscarTodosMaisAtual(Integer id);
}
