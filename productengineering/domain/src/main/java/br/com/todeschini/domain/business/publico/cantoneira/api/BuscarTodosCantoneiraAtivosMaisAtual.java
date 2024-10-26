package br.com.todeschini.domain.business.publico.cantoneira.api;

import br.com.todeschini.domain.business.publico.cantoneira.DCantoneira;

import java.util.List;

public interface BuscarTodosCantoneiraAtivosMaisAtual {

    List<DCantoneira> buscarTodosMaisAtual(Integer id);
}
