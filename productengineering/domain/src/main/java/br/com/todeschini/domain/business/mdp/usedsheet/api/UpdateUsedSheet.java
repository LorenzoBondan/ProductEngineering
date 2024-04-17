package br.com.todeschini.domain.business.mdp.usedsheet.api;

import br.com.todeschini.domain.business.mdp.usedsheet.DUsedSheet;

public interface UpdateUsedSheet {

    DUsedSheet update (Long id, DUsedSheet usedSheet);
}
