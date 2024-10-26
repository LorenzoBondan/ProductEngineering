package br.com.todeschini.domain.business.publico.history;

import br.com.todeschini.domain.business.publico.history.api.HistoryService;
import br.com.todeschini.domain.business.publico.history.spi.HistoryOperations;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Map;

@DomainService
public class HistoryServiceImpl implements HistoryService {

    private final HistoryOperations historyOperations;

    public HistoryServiceImpl(HistoryOperations historyOperations) {
        this.historyOperations = historyOperations;
    }

    @Override
    public <T> List<DHistory<T>> getHistoryEntityByRecord(Class<T> entityType, String tabname, String recordId, Map<String, Class<?>> attributeMappings) {
        return historyOperations.getHistoryEntityByRecord(entityType, tabname, recordId, attributeMappings);
    }
}
