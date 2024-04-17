package br.com.todeschini.domain.business.mdf.polyester.api;

import br.com.todeschini.domain.business.mdf.polyester.DPolyester;

import java.util.List;

public interface FindAllActivePolyesterAndCurrentOne {

    List<DPolyester> findAllActiveAndCurrentOne (Long id);
}
