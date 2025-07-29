package br.com.todeschini.lixeiraservicedomain.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class LixeiraIncluirEvent<T> extends ApplicationEvent {

    private final T entity;

    public LixeiraIncluirEvent(T entity) {
        super(entity);
        this.entity = entity;
    }
}
