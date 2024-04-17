package br.com.todeschini.domain.business.mdp.usedglue.api;

import org.springframework.stereotype.Component;

@Component
public interface UsedGlueService extends FindUsedGlue, InsertUsedGlue, UpdateUsedGlue, DeleteUsedGlue, InactivateUsedGlue,
        FindAllActiveUsedGlueAndCurrentOne {
}
