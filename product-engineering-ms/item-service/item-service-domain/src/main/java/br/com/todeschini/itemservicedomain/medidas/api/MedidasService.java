package br.com.todeschini.itemservicedomain.medidas.api;

import br.com.todeschini.itemservicedomain.medidas.DMedidas;
import br.com.todeschini.libvalidationhandler.contracts.BaseService;

import java.util.List;

public interface MedidasService extends BaseService<DMedidas> {

    List<DMedidas> buscarPorAlturaELarguraEEspessura (Integer altura, Integer largura, Integer espessura);
}
