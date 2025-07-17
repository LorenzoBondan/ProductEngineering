package br.com.todeschini.itemservicedomain.modelo.spi;

import br.com.todeschini.itemservicedomain.modelo.DModelo;
import br.com.todeschini.libvalidationhandler.contracts.SimpleCrud;

import java.util.List;

public interface CrudModelo extends SimpleCrud<DModelo, Integer> {

    List<DModelo> pesquisarPorDescricao (String descricao);
}
