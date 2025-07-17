package br.com.todeschini.itemservicedomain.acessoriousado.spi;

import br.com.todeschini.itemservicedomain.acessoriousado.DAcessorioUsado;
import br.com.todeschini.libvalidationhandler.contracts.SimpleCrud;

import java.util.List;

public interface CrudAcessorioUsado extends SimpleCrud<DAcessorioUsado, Integer> {

    List<DAcessorioUsado> pesquisarPorAcessorioEFilho (Integer cdacessorio, Integer cdfilho);
}
