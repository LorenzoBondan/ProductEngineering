package br.com.todeschini.domain.business.guides.guide.api;

import br.com.todeschini.domain.business.guides.guide.DGuide;

import java.util.List;

public interface FindAllActiveGuideAndCurrentOne {

    List<DGuide> findAllActiveAndCurrentOne (Long id);
}
