package br.com.todeschini.domain.business.publico.material.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.publico.material.DMaterial;

import java.util.Collection;

public interface CrudMaterial extends SimpleCrud<DMaterial, Long> {

    Collection<? extends DMaterial> findByName (String name);
}
