package br.com.todeschini.domain.business.publico.roteiro.api;

public interface RoteiroService extends BuscarRoteiro, CriarRoteiro, AlterarRoteiro, RemoverRoteiro, InativarRoteiro,
        BuscarTodosRoteiroAtivosMaisAtual, BuscarHistoricoRoteiro, SubstituirRoteiroPorVersaoAntiga,
        AlterarRoteiroEmLote, BuscarAtributosEditaveisEmLoteRoteiro {
}
