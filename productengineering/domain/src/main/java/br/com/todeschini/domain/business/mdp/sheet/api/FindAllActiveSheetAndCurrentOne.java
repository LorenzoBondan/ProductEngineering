package br.com.todeschini.domain.business.mdp.sheet.api;

import br.com.todeschini.domain.business.mdp.sheet.DSheet;

import java.util.List;

public interface FindAllActiveSheetAndCurrentOne {

    List<DSheet> findAllActiveAndCurrentOne (Long id);
}
