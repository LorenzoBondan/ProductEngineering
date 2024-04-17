package br.com.todeschini.domain.business.mdp.usedsheet.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.mdp.usedsheet.DUsedSheet;

import java.util.Collection;

public interface CrudUsedSheet extends SimpleCrud<DUsedSheet, Long> {

    Collection<? extends DUsedSheet> findBySheetAndMDPSon (Long SheetId, Long mdpSonId);
}
