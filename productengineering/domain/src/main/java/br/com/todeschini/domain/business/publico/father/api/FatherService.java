package br.com.todeschini.domain.business.publico.father.api;

import org.springframework.stereotype.Component;

@Component
public interface FatherService extends FindFather, InsertFather, UpdateFather, DeleteFather, InactivateFather,
        FindAllActiveFatherAndCurrentOne {
}
