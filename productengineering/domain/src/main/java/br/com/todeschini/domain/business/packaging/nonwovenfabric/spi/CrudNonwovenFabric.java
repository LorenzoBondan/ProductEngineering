package br.com.todeschini.domain.business.packaging.nonwovenfabric.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.packaging.nonwovenfabric.DNonwovenFabric;

import java.util.Collection;

public interface CrudNonwovenFabric extends SimpleCrud<DNonwovenFabric, Long> {

    Collection<? extends DNonwovenFabric> findByDescription (String description);
}
