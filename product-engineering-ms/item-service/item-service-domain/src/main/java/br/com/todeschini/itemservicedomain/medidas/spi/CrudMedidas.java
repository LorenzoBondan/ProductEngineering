package br.com.todeschini.itemservicedomain.medidas.spi;

import br.com.todeschini.itemservicedomain.medidas.DMedidas;
import br.com.todeschini.libvalidationhandler.contracts.SimpleCrud;

import java.util.List;

public interface CrudMedidas extends SimpleCrud<DMedidas, Integer> {

    List<DMedidas> pesquisarPorAlturaELarguraEEspessura (Integer altura, Integer largura, Integer espessura);
}
