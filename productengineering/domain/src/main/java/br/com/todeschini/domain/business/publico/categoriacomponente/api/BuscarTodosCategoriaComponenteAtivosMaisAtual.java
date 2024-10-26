package br.com.todeschini.domain.business.publico.categoriacomponente.api;

import br.com.todeschini.domain.business.publico.categoriacomponente.DCategoriaComponente;

import java.util.List;

public interface BuscarTodosCategoriaComponenteAtivosMaisAtual {

    List<DCategoriaComponente> buscarTodosMaisAtual(Integer id);
}
