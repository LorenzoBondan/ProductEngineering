package br.com.todeschini.lixeiraservicedomain.statusupdater;

import br.com.todeschini.libauditdomain.enums.DSituacaoEnum;

import java.util.Set;

public interface StatusUpdater {

    <T> void updateStatusAtivoRecursively(T entity, DSituacaoEnum newStatus, Set<Object> processedEntities, Boolean retrieveDependencies);
}

