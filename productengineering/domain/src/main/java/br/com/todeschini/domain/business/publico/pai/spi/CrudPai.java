package br.com.todeschini.domain.business.publico.pai.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.publico.pai.DPai;

import java.util.Collection;

public interface CrudPai extends SimpleCrud<DPai, Integer> {

    Collection<DPai> pesquisarPorDescricao (String descricao);
}
