package br.com.todeschini.domain.business.publico.cola.api;

public interface ColaService extends BuscarCola, CriarCola, AlterarCola, RemoverCola, InativarCola,
        BuscarTodosColaAtivosMaisAtual, BuscarHistoricoCola, SubstituirColaPorVersaoAntiga, AlterarColaEmLote,
        BuscarAtributosEditaveisEmLoteCola {
}
