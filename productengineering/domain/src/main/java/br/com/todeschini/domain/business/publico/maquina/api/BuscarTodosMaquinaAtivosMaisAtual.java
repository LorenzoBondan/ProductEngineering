package br.com.todeschini.domain.business.publico.maquina.api;

import br.com.todeschini.domain.business.publico.maquina.DMaquina;

import java.util.List;

public interface BuscarTodosMaquinaAtivosMaisAtual {

    List<DMaquina> buscarTodosMaisAtual(Integer id);
}
