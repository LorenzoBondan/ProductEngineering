package br.com.todeschini.mdpservicedomain.cola.spi;

import br.com.todeschini.libvalidationhandler.contracts.SimpleCrud;
import br.com.todeschini.mdpservicedomain.cola.DCola;

import java.util.List;

public interface CrudCola extends SimpleCrud<DCola, Integer> {

    List<DCola> pesquisarPorDescricao (String descricao);
}
