package br.com.todeschini.domain.business.mdp.usedsheet.api;

import br.com.todeschini.domain.business.mdp.usedsheet.DUsedSheet;

import java.util.List;

public interface FindAllActiveUsedSheetAndCurrentOne {

    List<DUsedSheet> findAllActiveAndCurrentOne (Long id);
}
