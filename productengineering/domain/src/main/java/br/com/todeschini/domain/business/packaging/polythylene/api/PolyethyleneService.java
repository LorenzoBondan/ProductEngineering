package br.com.todeschini.domain.business.packaging.polythylene.api;

import org.springframework.stereotype.Component;

@Component
public interface PolyethyleneService extends FindPolyethylene, InsertPolyethylene, UpdatePolyethylene, DeletePolyethylene, InactivatePolyethylene,
        FindAllActivePolyethyleneAndCurrentOne {
}
