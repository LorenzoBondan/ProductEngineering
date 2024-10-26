package br.com.todeschini.domain.business.publico.baguete.api;

import br.com.todeschini.domain.business.publico.baguete.DBaguete;

import java.util.List;

public interface BuscarTodosBagueteAtivosMaisAtual {

    List<DBaguete> buscarTodosMaisAtual(Integer id);
}
