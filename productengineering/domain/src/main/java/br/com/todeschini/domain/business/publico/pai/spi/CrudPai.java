package br.com.todeschini.domain.business.publico.pai.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.publico.pai.montadores.DMontadorEstruturaPai;
import br.com.todeschini.domain.business.publico.pai.DPai;
import br.com.todeschini.domain.business.publico.pai.montadores.DMontadorEstruturaPaiModulacao;

import java.util.Collection;

public interface CrudPai extends SimpleCrud<DPai, Integer> {

    Collection<? extends DPai> pesquisarPorDescricao (String descricao);
    DPai montarEstrutura (DMontadorEstruturaPai montadorEstruturaPai);
    DPai montarEstruturaModulacao (DMontadorEstruturaPaiModulacao montadorEstruturaPaiModulacao);
}
