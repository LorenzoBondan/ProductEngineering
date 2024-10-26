package br.com.todeschini.domain.business.publico.filho.api;

import br.com.todeschini.domain.business.publico.filho.DFilho;

import java.util.List;

public interface BuscarTodosFilhoAtivosMaisAtual {

    List<DFilho> buscarTodosMaisAtual(Integer id);
}
