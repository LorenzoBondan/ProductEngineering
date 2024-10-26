package br.com.todeschini.domain.business.publico.tnt.api;

import br.com.todeschini.domain.business.publico.tnt.DTnt;

import java.util.List;

public interface BuscarTodosTntAtivosMaisAtual {

    List<DTnt> buscarTodosMaisAtual(Integer id);
}
