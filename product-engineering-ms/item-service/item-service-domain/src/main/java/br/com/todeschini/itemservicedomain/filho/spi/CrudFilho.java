package br.com.todeschini.itemservicedomain.filho.spi;

import br.com.todeschini.itemservicedomain.filho.DFilho;
import br.com.todeschini.libvalidationhandler.contracts.SimpleCrud;

import java.util.List;

public interface CrudFilho extends SimpleCrud<DFilho, Integer> {

    List<DFilho> pesquisarPorDescricaoECorEMedidas (String descricao, Integer cdcor, Integer cdmedidas);
    List<DFilho> pesquisarPorDescricaoEMedidas (String descricao, Integer cdmedidas);
}
