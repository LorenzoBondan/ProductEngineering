package br.com.todeschini.domain.business.publico.pai.api;

import br.com.todeschini.domain.business.publico.pai.DPai;

import java.util.List;

public interface BuscarTodosPaiAtivosMaisAtual {

    List<DPai> buscarTodosMaisAtual(Integer id);
}
