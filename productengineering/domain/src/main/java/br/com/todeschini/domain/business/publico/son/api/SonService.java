package br.com.todeschini.domain.business.publico.son.api;

import org.springframework.stereotype.Component;

@Component
public interface SonService extends FindSon, InsertSon, UpdateSon, DeleteSon, InactivateSon,
        FindAllActiveSonAndCurrentOne {
}
