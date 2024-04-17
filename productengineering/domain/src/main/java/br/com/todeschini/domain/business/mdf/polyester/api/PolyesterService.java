package br.com.todeschini.domain.business.mdf.polyester.api;

import org.springframework.stereotype.Component;

@Component
public interface PolyesterService extends FindPolyester, InsertPolyester, UpdatePolyester, DeletePolyester, InactivatePolyester,
        FindAllActivePolyesterAndCurrentOne {
}
