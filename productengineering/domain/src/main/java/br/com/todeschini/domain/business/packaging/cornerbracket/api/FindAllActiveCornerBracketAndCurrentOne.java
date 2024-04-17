package br.com.todeschini.domain.business.packaging.cornerbracket.api;

import br.com.todeschini.domain.business.packaging.cornerbracket.DCornerBracket;

import java.util.List;

public interface FindAllActiveCornerBracketAndCurrentOne {

    List<DCornerBracket> findAllActiveAndCurrentOne (Long id);
}
