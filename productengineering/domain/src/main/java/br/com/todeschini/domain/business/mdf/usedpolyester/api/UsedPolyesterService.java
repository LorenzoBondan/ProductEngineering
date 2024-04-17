package br.com.todeschini.domain.business.mdf.usedpolyester.api;

import org.springframework.stereotype.Component;

@Component
public interface UsedPolyesterService extends FindUsedPolyester, InsertUsedPolyester, UpdateUsedPolyester, DeleteUsedPolyester, InactivateUsedPolyester,
        FindAllActiveUsedPolyesterAndCurrentOne {
}
