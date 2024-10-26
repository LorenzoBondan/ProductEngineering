package br.com.todeschini.domain.business.publico.modelo.api;

import br.com.todeschini.domain.business.publico.modelo.DModelo;

import java.util.List;

public interface BuscarTodosModeloAtivosMaisAtual {

    List<DModelo> buscarTodosMaisAtual(Integer id);
}
