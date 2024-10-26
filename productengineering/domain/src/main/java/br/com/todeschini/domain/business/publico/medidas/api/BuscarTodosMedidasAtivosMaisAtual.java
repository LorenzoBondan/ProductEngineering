package br.com.todeschini.domain.business.publico.medidas.api;

import br.com.todeschini.domain.business.publico.medidas.DMedidas;

import java.util.List;

public interface BuscarTodosMedidasAtivosMaisAtual {

    List<DMedidas> buscarTodosMaisAtual(Integer id);
}
