package br.com.todeschini.domain.business.packaging.usedpolyethylene.api;

import org.springframework.stereotype.Component;

@Component
public interface UsedPolyethyleneService extends FindUsedPolyethylene, InsertUsedPolyethylene, UpdateUsedPolyethylene, DeleteUsedPolyethylene, InactivateUsedPolyethylene,
        FindAllActiveUsedPolyethyleneAndCurrentOne {
}
