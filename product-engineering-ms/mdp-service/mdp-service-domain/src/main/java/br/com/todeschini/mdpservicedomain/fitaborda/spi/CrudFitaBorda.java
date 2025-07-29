package br.com.todeschini.mdpservicedomain.fitaborda.spi;

import br.com.todeschini.libvalidationhandler.contracts.SimpleCrud;
import br.com.todeschini.mdpservicedomain.fitaborda.DFitaBorda;

import java.util.List;

public interface CrudFitaBorda extends SimpleCrud<DFitaBorda, Integer> {

    List<DFitaBorda> pesquisarPorDescricao (String descricao);
}
