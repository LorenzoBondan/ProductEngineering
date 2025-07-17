package br.com.todeschini.itemservicedomain.acessorio.spi;

import br.com.todeschini.itemservicedomain.acessorio.DAcessorio;
import br.com.todeschini.libvalidationhandler.contracts.SimpleCrud;

import java.util.List;

public interface CrudAcessorio extends SimpleCrud<DAcessorio, Integer> {

    List<DAcessorio> pesquisarPorDescricao (String descricao);
}
