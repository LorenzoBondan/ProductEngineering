package br.com.todeschini.domain.business.publico.polietileno.api;

import br.com.todeschini.domain.business.publico.polietileno.DPolietileno;

import java.util.List;

public interface BuscarTodosPolietilenoAtivosMaisAtual {

    List<DPolietileno> buscarTodosMaisAtual(Integer id);
}
