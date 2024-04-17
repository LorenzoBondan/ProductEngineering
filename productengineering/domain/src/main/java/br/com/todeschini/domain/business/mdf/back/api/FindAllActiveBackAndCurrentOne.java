package br.com.todeschini.domain.business.mdf.back.api;

import br.com.todeschini.domain.business.mdf.back.DBack;

import java.util.List;

public interface FindAllActiveBackAndCurrentOne {

    List<DBack> findAllActiveAndCurrentOne (Long id);
}
