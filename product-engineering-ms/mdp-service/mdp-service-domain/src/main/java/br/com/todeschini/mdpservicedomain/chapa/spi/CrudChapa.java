package br.com.todeschini.mdpservicedomain.chapa.spi;

import br.com.todeschini.libvalidationhandler.contracts.SimpleCrud;
import br.com.todeschini.mdpservicedomain.chapa.DChapa;

import java.util.List;

public interface CrudChapa extends SimpleCrud<DChapa, Integer> {

    List<DChapa> pesquisarPorDescricao (String descricao);
}
