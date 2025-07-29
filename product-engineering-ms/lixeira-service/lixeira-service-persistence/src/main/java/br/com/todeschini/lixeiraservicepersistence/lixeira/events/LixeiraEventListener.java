package br.com.todeschini.lixeiraservicepersistence.lixeira.events;

import br.com.todeschini.lixeiraservicedomain.events.LixeiraIncluirEvent;
import br.com.todeschini.lixeiraservicedomain.events.LixeiraRemoverEvent;
import br.com.todeschini.lixeiraservicedomain.lixeira.spi.LixeiraOperations;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LixeiraEventListener {

    private final LixeiraOperations lixeiraOperations;

    @EventListener
    @Async
    public <T> void handleIncluirEvent(LixeiraIncluirEvent<T> event) throws IllegalAccessException {
        lixeiraOperations.incluir(event.getEntity());
    }

    @EventListener
    @Async
    public <T> void handleRemoverEvent(LixeiraRemoverEvent<T> event) throws IllegalAccessException {
        lixeiraOperations.remover(event.getEntity());
    }
}

