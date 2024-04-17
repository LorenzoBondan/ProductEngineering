package br.com.todeschini.domain.business.packaging.usedpolyethylene.api;

import br.com.todeschini.domain.business.packaging.usedpolyethylene.DUsedPolyethylene;

import java.util.List;

public interface FindAllActiveUsedPolyethyleneAndCurrentOne {

    List<DUsedPolyethylene> findAllActiveAndCurrentOne (Long id);
}
