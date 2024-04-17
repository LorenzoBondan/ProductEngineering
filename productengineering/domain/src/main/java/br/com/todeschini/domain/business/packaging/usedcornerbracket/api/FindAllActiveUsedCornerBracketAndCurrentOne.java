package br.com.todeschini.domain.business.packaging.usedcornerbracket.api;

import br.com.todeschini.domain.business.packaging.usedcornerbracket.DUsedCornerBracket;

import java.util.List;

public interface FindAllActiveUsedCornerBracketAndCurrentOne {

    List<DUsedCornerBracket> findAllActiveAndCurrentOne (Long id);
}
