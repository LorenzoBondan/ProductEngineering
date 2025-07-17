package br.com.todeschini.itemservicedomain.filho.api;

import br.com.todeschini.itemservicedomain.filho.DFilho;
import br.com.todeschini.libvalidationhandler.contracts.BaseService;

import java.util.List;

public interface FilhoService extends BaseService<DFilho> {

    List<DFilho> pesquisarPorDescricaoEMedidas (String descricao, Integer cdmedidas); // fundos
}
