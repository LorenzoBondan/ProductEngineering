package br.com.todeschini.domain.business.publico.plastico.api;

import br.com.todeschini.domain.business.publico.plastico.DPlastico;

import java.util.List;

public interface BuscarTodosPlasticoAtivosMaisAtual {

    List<DPlastico> buscarTodosMaisAtual(Integer id);
}
