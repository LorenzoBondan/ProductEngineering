package br.com.todeschini.domain.business.publico.materialusado.api;

import br.com.todeschini.domain.business.publico.materialusado.DMaterialUsado;

import java.util.List;

public interface BuscarTodosMaterialUsadoAtivosMaisAtual {

    List<DMaterialUsado> buscarTodosMaisAtual(Integer id);
}
