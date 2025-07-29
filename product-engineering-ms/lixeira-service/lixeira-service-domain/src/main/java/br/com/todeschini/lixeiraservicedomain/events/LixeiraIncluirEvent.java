package br.com.todeschini.lixeiraservicedomain.events;

import org.springframework.context.ApplicationEvent;

public class LixeiraIncluirEvent<T> extends ApplicationEvent {

    private final T entity;

    public LixeiraIncluirEvent(T entity) {
        super(entity);
        this.entity = entity;
    }

    public T getEntity() {
        return entity;
    }
}
