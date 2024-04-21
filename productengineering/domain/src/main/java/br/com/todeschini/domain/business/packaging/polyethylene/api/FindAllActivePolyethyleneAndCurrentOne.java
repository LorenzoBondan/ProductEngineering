package br.com.todeschini.domain.business.packaging.polyethylene.api;

import br.com.todeschini.domain.business.packaging.polyethylene.DPolyethylene;

import java.util.List;

public interface FindAllActivePolyethyleneAndCurrentOne {

    List<DPolyethylene> findAllActiveAndCurrentOne (Long id);
}
