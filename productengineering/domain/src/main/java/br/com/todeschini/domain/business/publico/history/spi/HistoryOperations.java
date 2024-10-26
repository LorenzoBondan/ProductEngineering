package br.com.todeschini.domain.business.publico.history.spi;

import br.com.todeschini.domain.business.publico.history.DHistory;

import java.util.List;
import java.util.Map;

public interface HistoryOperations {

    <T> List<DHistory<T>> getHistoryEntityByRecord(Class<T> entityType, String tabname, String recordId, Map<String, Class<?>> attributeMappings);
}
