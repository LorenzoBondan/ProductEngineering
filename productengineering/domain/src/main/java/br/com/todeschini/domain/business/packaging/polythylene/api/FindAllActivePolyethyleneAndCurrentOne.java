package br.com.todeschini.domain.business.packaging.polythylene.api;

import br.com.todeschini.domain.business.packaging.polythylene.DPolyethylene;

import java.util.List;

public interface FindAllActivePolyethyleneAndCurrentOne {

    List<DPolyethylene> findAllActiveAndCurrentOne (Long id);
}
