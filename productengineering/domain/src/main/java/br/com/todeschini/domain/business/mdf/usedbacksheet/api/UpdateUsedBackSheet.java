package br.com.todeschini.domain.business.mdf.usedbacksheet.api;

import br.com.todeschini.domain.business.mdf.usedbacksheet.DUsedBackSheet;

public interface UpdateUsedBackSheet {

    DUsedBackSheet update (Long id, DUsedBackSheet usedBackSheet);
}
