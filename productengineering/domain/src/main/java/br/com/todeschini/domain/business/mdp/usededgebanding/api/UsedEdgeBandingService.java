package br.com.todeschini.domain.business.mdp.usededgebanding.api;

import org.springframework.stereotype.Component;

@Component
public interface UsedEdgeBandingService extends FindUsedEdgeBanding, InsertUsedEdgeBanding, UpdateUsedEdgeBanding, DeleteUsedEdgeBanding, InactivateUsedEdgeBanding,
        FindAllActiveUsedEdgeBandingAndCurrentOne {
}
