package br.com.todeschini.domain.business.mdf.usedbacksheet.api;

import br.com.todeschini.domain.business.mdf.usedbacksheet.DUsedBackSheet;

import java.util.List;

public interface FindAllActiveUsedBackSheetAndCurrentOne {

    List<DUsedBackSheet> findAllActiveAndCurrentOne (Long id);
}
