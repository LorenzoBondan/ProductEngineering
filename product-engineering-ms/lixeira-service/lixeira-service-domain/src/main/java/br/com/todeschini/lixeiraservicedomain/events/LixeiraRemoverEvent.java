package br.com.todeschini.lixeiraservicedomain.events;

import org.springframework.context.ApplicationEvent;

public class LixeiraRemoverEvent<T> extends ApplicationEvent {

    private final T entity;

    public LixeiraRemoverEvent(T entity) {
        super(entity);
        this.entity = entity;
    }

    public T getEntity() {
        return entity;
    }
}

