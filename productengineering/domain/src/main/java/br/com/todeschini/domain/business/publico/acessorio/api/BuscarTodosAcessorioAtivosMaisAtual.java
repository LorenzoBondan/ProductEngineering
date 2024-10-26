package br.com.todeschini.domain.business.publico.acessorio.api;

import br.com.todeschini.domain.business.publico.acessorio.DAcessorio;

import java.util.List;

public interface BuscarTodosAcessorioAtivosMaisAtual {

    List<DAcessorio> buscarTodosMaisAtual(Integer id);
}
