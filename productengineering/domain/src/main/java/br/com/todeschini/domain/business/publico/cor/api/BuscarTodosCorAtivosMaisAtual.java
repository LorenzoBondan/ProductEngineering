package br.com.todeschini.domain.business.publico.cor.api;

import br.com.todeschini.domain.business.publico.cor.DCor;

import java.util.List;

public interface BuscarTodosCorAtivosMaisAtual {

    List<DCor> buscarTodosMaisAtual(Integer id);
}
