package br.com.todeschini.domain.business.mdf.usedbacksheet.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.mdf.usedbacksheet.DUsedBackSheet;

import java.util.Collection;

public interface CrudUsedBackSheet extends SimpleCrud<DUsedBackSheet, Long> {

    Collection<? extends DUsedBackSheet> findByBackAndSheet (Long backId, Long sheetId);
}
