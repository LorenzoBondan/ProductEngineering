package br.com.todeschini.domain.business.publico.chapa.api;

import br.com.todeschini.domain.business.publico.chapa.DChapa;

import java.util.List;

public interface BuscarTodosChapaAtivosMaisAtual {

    List<DChapa> buscarTodosMaisAtual(Integer id);
}
