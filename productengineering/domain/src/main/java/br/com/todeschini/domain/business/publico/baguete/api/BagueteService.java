package br.com.todeschini.domain.business.publico.baguete.api;

public interface BagueteService extends BuscarBaguete, CriarBaguete, AlterarBaguete, RemoverBaguete, InativarBaguete,
        BuscarTodosBagueteAtivosMaisAtual, BuscarHistoricoBaguete, SubstituirBaguetePorVersaoAntiga,
        AlterarBagueteEmLote, BuscarAtributosEditaveisEmLoteBaguete {
}
