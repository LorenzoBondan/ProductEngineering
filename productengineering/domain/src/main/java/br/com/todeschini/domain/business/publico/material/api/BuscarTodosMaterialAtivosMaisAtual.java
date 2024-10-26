package br.com.todeschini.domain.business.publico.material.api;

import br.com.todeschini.domain.business.publico.material.DMaterial;

import java.util.List;

public interface BuscarTodosMaterialAtivosMaisAtual {

    List<DMaterial> buscarTodosMaisAtual(Integer id);
}
