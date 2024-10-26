package br.com.todeschini.domain.business.publico.fitaborda.api;

import br.com.todeschini.domain.business.publico.fitaborda.DFitaBorda;

import java.util.List;

public interface BuscarTodosFitaBordaAtivosMaisAtual {

    List<DFitaBorda> buscarTodosMaisAtual(Integer id);
}
