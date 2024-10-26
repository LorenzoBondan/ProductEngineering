package br.com.todeschini.domain.business.publico.history.api;

import br.com.todeschini.domain.business.publico.history.DHistory;

import java.util.List;
import java.util.Map;

public interface BuscarHistory {

    <T> List<DHistory<T>> getHistoryEntityByRecord(Class<T> entityType, String tabname, String recordId, Map<String, Class<?>> attributeMappings);
}
