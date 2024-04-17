package br.com.todeschini.domain.business.aluminium.drawerpull.api;

import br.com.todeschini.domain.business.aluminium.drawerpull.DDrawerPull;

import java.util.List;

public interface FindAllActiveDrawerPullAndCurrentOne {

    List<DDrawerPull> findAllActiveAndCurrentOne (Long id);
}
