package br.com.todeschini.domain.business.publico.tnt.api;

public interface TntService extends BuscarTnt, CriarTnt, AlterarTnt, RemoverTnt, InativarTnt,
        BuscarTodosTntAtivosMaisAtual, BuscarHistoricoTnt, SubstituirTntPorVersaoAntiga,
        AlterarTntEmLote, BuscarAtributosEditaveisEmLoteTnt {
}
