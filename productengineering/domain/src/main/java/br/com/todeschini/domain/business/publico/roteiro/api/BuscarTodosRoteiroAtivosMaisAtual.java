package br.com.todeschini.domain.business.publico.roteiro.api;

import br.com.todeschini.domain.business.publico.roteiro.DRoteiro;

import java.util.List;

public interface BuscarTodosRoteiroAtivosMaisAtual {

    List<DRoteiro> buscarTodosMaisAtual(Integer id);
}
