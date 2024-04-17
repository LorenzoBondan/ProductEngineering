package br.com.todeschini.domain.business.aluminium.usedscrew.api;

import org.springframework.stereotype.Component;

@Component
public interface UsedScrewService extends FindUsedScrew, InsertUsedScrew, UpdateUsedScrew, DeleteUsedScrew, InactivateUsedScrew,
        FindAllActiveUsedScrewAndCurrentOne {
}
