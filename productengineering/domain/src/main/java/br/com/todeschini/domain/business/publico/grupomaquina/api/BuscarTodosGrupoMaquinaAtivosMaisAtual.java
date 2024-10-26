package br.com.todeschini.domain.business.publico.grupomaquina.api;

import br.com.todeschini.domain.business.publico.grupomaquina.DGrupoMaquina;

import java.util.List;

public interface BuscarTodosGrupoMaquinaAtivosMaisAtual {

    List<DGrupoMaquina> buscarTodosMaisAtual(Integer id);
}
