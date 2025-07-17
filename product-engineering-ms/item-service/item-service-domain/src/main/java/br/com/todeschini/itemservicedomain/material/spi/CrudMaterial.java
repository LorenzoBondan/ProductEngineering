package br.com.todeschini.itemservicedomain.material.spi;

import br.com.todeschini.itemservicedomain.material.DMaterial;
import br.com.todeschini.libvalidationhandler.contracts.SimpleCrud;

import java.util.List;

public interface CrudMaterial extends SimpleCrud<DMaterial, Integer> {

    List<DMaterial> pesquisarPorDescricao (String descricao);
}
