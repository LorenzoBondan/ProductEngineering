package br.com.todeschini.domain.business.packaging.usedplastic.api;

import br.com.todeschini.domain.business.packaging.usedplastic.DUsedPlastic;

import java.util.List;

public interface FindAllActiveUsedPlasticAndCurrentOne {

    List<DUsedPlastic> findAllActiveAndCurrentOne (Long id);
}
