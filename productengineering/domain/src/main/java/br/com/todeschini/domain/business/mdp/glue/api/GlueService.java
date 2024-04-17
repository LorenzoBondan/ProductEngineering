package br.com.todeschini.domain.business.mdp.glue.api;

import org.springframework.stereotype.Component;

@Component
public interface GlueService extends FindGlue, InsertGlue, UpdateGlue, DeleteGlue, InactivateGlue,
        FindAllActiveGlueAndCurrentOne {
}
