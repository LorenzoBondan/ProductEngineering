package br.com.todeschini.domain.business.publico.chapa.api;

public interface ChapaService extends BuscarChapa, CriarChapa, AlterarChapa, RemoverChapa, InativarChapa,
        BuscarTodosChapaAtivosMaisAtual, BuscarHistoricoChapa, SubstituirChapaPorVersaoAntiga,
        AlterarChapaEmLote, BuscarAtributosEditaveisEmLoteChapa {
}
