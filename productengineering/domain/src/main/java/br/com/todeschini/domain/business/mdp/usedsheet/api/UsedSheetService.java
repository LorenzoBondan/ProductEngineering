package br.com.todeschini.domain.business.mdp.usedsheet.api;

import org.springframework.stereotype.Component;

@Component
public interface UsedSheetService extends FindUsedSheet, InsertUsedSheet, UpdateUsedSheet, DeleteUsedSheet, InactivateUsedSheet,
        FindAllActiveUsedSheetAndCurrentOne {
}
