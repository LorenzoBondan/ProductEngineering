package br.com.todeschini.domain.business.guides.guide.api;

import br.com.todeschini.domain.business.guides.guide.DGuide;

public interface UpdateGuide {

    DGuide update (Long id, DGuide Guide);
}
