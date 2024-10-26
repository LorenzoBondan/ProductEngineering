package br.com.todeschini.domain.business.publico.user.api;

import br.com.todeschini.domain.business.publico.user.DUser;

import java.util.List;

public interface BuscarTodosUserAtivosMaisAtual {

    List<DUser> buscarTodosMaisAtual(Integer id);
}
