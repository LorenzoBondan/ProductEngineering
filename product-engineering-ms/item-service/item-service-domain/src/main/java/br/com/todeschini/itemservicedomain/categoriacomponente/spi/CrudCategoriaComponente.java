package br.com.todeschini.itemservicedomain.categoriacomponente.spi;

import br.com.todeschini.itemservicedomain.categoriacomponente.DCategoriaComponente;
import br.com.todeschini.libvalidationhandler.contracts.SimpleCrud;

import java.util.List;

public interface CrudCategoriaComponente extends SimpleCrud<DCategoriaComponente, Integer> {

    List<DCategoriaComponente> pesquisarPorDescricao (String descricao);
}
