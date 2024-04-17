package br.com.todeschini.domain.business.aluminium.aluminiumson.api;

import org.springframework.stereotype.Component;

@Component
public interface AluminiumSonService extends FindAluminiumSon, InsertAluminiumSon, UpdateAluminiumSon, DeleteAluminiumSon, InactivateAluminiumSon,
        FindAllActiveAluminiumSonAndCurrentOne {
}
