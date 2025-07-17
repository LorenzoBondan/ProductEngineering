package br.com.todeschini.itemservicedomain.pai.spi;

import br.com.todeschini.itemservicedomain.pai.DPai;
import br.com.todeschini.libvalidationhandler.contracts.SimpleCrud;

import java.util.Collection;
import java.util.List;

public interface CrudPai extends SimpleCrud<DPai, Integer> {

    Collection<DPai> pesquisarPorDescricao (String descricao);
    List<DPai> buscarPorModelo(Integer cdmodelo);
    List<DPai> buscarPorCategoriaComponente(Integer cdcategoriaComponente);
}
