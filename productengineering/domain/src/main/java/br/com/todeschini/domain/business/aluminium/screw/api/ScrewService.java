package br.com.todeschini.domain.business.aluminium.screw.api;

import org.springframework.stereotype.Component;

@Component
public interface ScrewService extends FindScrew, InsertScrew, UpdateScrew, DeleteScrew, InactivateScrew,
        FindAllActiveScrewAndCurrentOne {
}
