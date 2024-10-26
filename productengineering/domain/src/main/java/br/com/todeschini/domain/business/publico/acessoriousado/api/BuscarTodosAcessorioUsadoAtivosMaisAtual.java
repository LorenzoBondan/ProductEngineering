package br.com.todeschini.domain.business.publico.acessoriousado.api;

import br.com.todeschini.domain.business.publico.acessoriousado.DAcessorioUsado;

import java.util.List;

public interface BuscarTodosAcessorioUsadoAtivosMaisAtual {

    List<DAcessorioUsado> buscarTodosMaisAtual(Integer id);
}
