package br.com.todeschini.domain.business.packaging.ghost.api;

import org.springframework.stereotype.Component;

@Component
public interface GhostService extends FindGhost, InsertGhost, UpdateGhost, DeleteGhost, InactivateGhost,
        FindAllActiveGhostAndCurrentOne {
}
