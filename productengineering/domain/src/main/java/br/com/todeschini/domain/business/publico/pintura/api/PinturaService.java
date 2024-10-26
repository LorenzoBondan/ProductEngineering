package br.com.todeschini.domain.business.publico.pintura.api;

public interface PinturaService extends BuscarPintura, CriarPintura, AlterarPintura, RemoverPintura, InativarPintura,
        BuscarTodosPinturaAtivosMaisAtual, BuscarHistoricoPintura, SubstituirPinturaPorVersaoAntiga,
        AlterarPinturaEmLote, BuscarAtributosEditaveisEmLotePintura {
}
