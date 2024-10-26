package br.com.todeschini.domain.business.publico.materialusado.api;

public interface MaterialUsadoService extends BuscarMaterialUsado, CriarMaterialUsado, AlterarMaterialUsado, RemoverMaterialUsado, InativarMaterialUsado, BuscarTodosMaterialUsadoAtivosMaisAtual,
        BuscarHistoricoMaterialUsado, SubstituirMaterialUsadoPorVersaoAntiga, BuscarAtributosEditaveisEmLoteMaterialUsado,
        AlterarMaterialUsadoEmLote {
}
