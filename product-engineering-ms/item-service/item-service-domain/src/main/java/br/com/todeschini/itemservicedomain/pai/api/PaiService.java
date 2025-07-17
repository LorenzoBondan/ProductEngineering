package br.com.todeschini.itemservicedomain.pai.api;

import br.com.todeschini.itemservicedomain.pai.DPai;
import br.com.todeschini.itemservicedomain.pai.montadores.DMontadorEstruturaPai;
import br.com.todeschini.itemservicedomain.pai.montadores.DMontadorEstruturaPaiModulacao;
import br.com.todeschini.libvalidationhandler.contracts.BaseService;

import java.util.List;

public interface PaiService extends BaseService<DPai> {

    DPai montarEstrutura (DMontadorEstruturaPai montadorEstruturaPai);
    DPai montarEstruturaModulacao (DMontadorEstruturaPaiModulacao montadorEstruturaPaiModulacao);
    List<DPai> buscarPorModelo(Integer cdmodelo);
    List<DPai> buscarPorCategoriaComponente(Integer cdcategoriaComponente);
}
