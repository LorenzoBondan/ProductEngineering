package br.com.todeschini.domain.business.publico.pinturabordafundo.api;

import br.com.todeschini.domain.business.publico.pinturabordafundo.DPinturaBordaFundo;

import java.util.List;

public interface BuscarTodosPinturaBordaFundoAtivosMaisAtual {

    List<DPinturaBordaFundo> buscarTodosMaisAtual(Integer id);
}
