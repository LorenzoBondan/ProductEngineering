package br.com.todeschini.domain.business.packaging.nonwovenfabric.api;

import br.com.todeschini.domain.business.packaging.nonwovenfabric.DNonwovenFabric;

import java.util.List;

public interface FindAllActiveNonwovenFabricAndCurrentOne {

    List<DNonwovenFabric> findAllActiveAndCurrentOne (Long id);
}
