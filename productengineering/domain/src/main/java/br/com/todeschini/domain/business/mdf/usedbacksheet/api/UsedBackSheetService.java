package br.com.todeschini.domain.business.mdf.usedbacksheet.api;

import org.springframework.stereotype.Component;

@Component
public interface UsedBackSheetService extends FindUsedBackSheet, InsertUsedBackSheet, UpdateUsedBackSheet, DeleteUsedBackSheet, InactivateUsedBackSheet,
        FindAllActiveUsedBackSheetAndCurrentOne {
}
