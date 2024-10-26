package br.com.todeschini.domain.business.publico.tnt.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.publico.tnt.DTnt;

import java.util.Collection;

public interface CrudTnt extends SimpleCrud<DTnt, Integer> {

    Collection<? extends DTnt> pesquisarPorDescricao (String descricao);
}
