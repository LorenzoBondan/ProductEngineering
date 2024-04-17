package br.com.todeschini.domain.business.aluminium.aluminiumtype.api;

import org.springframework.stereotype.Component;

@Component
public interface AluminiumTypeService extends FindAluminiumType, InsertAluminiumType, UpdateAluminiumType, DeleteAluminiumType, InactivateAluminiumType,
        FindAllActiveAluminiumTypeAndCurrentOne {
}
