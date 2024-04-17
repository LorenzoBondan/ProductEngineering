package br.com.todeschini.domain.business.mdp.edgebanding.api;

import org.springframework.stereotype.Component;

@Component
public interface EdgeBandingService extends FindEdgeBanding, InsertEdgeBanding, UpdateEdgeBanding, DeleteEdgeBanding, InactivateEdgeBanding,
        FindAllActiveEdgeBandingAndCurrentOne {
}
