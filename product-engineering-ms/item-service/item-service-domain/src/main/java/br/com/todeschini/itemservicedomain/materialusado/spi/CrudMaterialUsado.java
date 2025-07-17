package br.com.todeschini.itemservicedomain.materialusado.spi;

import br.com.todeschini.itemservicedomain.materialusado.DMaterialUsado;
import br.com.todeschini.libvalidationhandler.contracts.SimpleCrud;

import java.util.List;

public interface CrudMaterialUsado extends SimpleCrud<DMaterialUsado, Integer> {

    List<DMaterialUsado> pesquisarPorFilhoEMaterial (Integer cdfilho, Integer cdmaterial);
}
