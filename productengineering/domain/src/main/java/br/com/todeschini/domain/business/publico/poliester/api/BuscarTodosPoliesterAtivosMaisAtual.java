package br.com.todeschini.domain.business.publico.poliester.api;

import br.com.todeschini.domain.business.publico.poliester.DPoliester;

import java.util.List;

public interface BuscarTodosPoliesterAtivosMaisAtual {

    List<DPoliester> buscarTodosMaisAtual(Integer id);
}
