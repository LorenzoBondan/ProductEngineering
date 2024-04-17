package br.com.todeschini.domain.business.mdf.usedpolyester.api;

import br.com.todeschini.domain.business.mdf.usedpolyester.DUsedPolyester;

import java.util.List;

public interface FindAllActiveUsedPolyesterAndCurrentOne {

    List<DUsedPolyester> findAllActiveAndCurrentOne (Long id);
}
