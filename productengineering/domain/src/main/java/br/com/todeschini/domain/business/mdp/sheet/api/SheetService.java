package br.com.todeschini.domain.business.mdp.sheet.api;

import org.springframework.stereotype.Component;

@Component
public interface SheetService extends FindSheet, InsertSheet, UpdateSheet, DeleteSheet, InactivateSheet,
        FindAllActiveSheetAndCurrentOne {
}
