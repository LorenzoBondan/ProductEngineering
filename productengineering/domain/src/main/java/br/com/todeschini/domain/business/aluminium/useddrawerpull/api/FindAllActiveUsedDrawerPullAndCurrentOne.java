package br.com.todeschini.domain.business.aluminium.useddrawerpull.api;

import br.com.todeschini.domain.business.aluminium.useddrawerpull.DUsedDrawerPull;

import java.util.List;

public interface FindAllActiveUsedDrawerPullAndCurrentOne {

    List<DUsedDrawerPull> findAllActiveAndCurrentOne (Long id);
}
