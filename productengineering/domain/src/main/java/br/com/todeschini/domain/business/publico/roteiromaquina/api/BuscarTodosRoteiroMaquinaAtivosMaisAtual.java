package br.com.todeschini.domain.business.publico.roteiromaquina.api;

import br.com.todeschini.domain.business.publico.roteiromaquina.DRoteiroMaquina;

import java.util.List;

public interface BuscarTodosRoteiroMaquinaAtivosMaisAtual {

    List<DRoteiroMaquina> buscarTodosMaisAtual(Integer id);
}
