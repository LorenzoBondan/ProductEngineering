package br.com.todeschini.domain.business.packaging.usednonwovenfabric.api;

import br.com.todeschini.domain.business.packaging.usednonwovenfabric.DUsedNonwovenFabric;

import java.util.List;

public interface FindAllActiveUsedNonwovenFabricAndCurrentOne {

    List<DUsedNonwovenFabric> findAllActiveAndCurrentOne (Long id);
}
