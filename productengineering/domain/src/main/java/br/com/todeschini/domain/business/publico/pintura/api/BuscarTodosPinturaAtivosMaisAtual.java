package br.com.todeschini.domain.business.publico.pintura.api;

import br.com.todeschini.domain.business.publico.pintura.DPintura;

import java.util.List;

public interface BuscarTodosPinturaAtivosMaisAtual {

    List<DPintura> buscarTodosMaisAtual(Integer id);
}
