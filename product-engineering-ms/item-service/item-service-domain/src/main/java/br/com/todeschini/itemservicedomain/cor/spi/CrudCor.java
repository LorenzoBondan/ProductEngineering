package br.com.todeschini.itemservicedomain.cor.spi;

import br.com.todeschini.itemservicedomain.cor.DCor;
import br.com.todeschini.libvalidationhandler.contracts.SimpleCrud;

import java.util.List;

public interface CrudCor extends SimpleCrud<DCor, Integer> {

    List<DCor> pesquisarPorDescricao (String descricao);
}
