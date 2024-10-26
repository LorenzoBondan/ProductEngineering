package br.com.todeschini.domain.business.publico.cantoneira.api;

public interface CantoneiraService extends BuscarCantoneira, CriarCantoneira, AlterarCantoneira, RemoverCantoneira, InativarCantoneira,
        BuscarTodosCantoneiraAtivosMaisAtual, BuscarHistoricoCantoneira, SubstituirCantoneiraPorVersaoAntiga,
        AlterarCantoneiraEmLote, BuscarAtributosEditaveisEmLoteCantoneira {
}
