package br.com.todeschini.lixeiraservicepersistence.lixeira.events;

import br.com.todeschini.lixeiraservicedomain.events.LixeiraIncluirEvent;
import br.com.todeschini.lixeiraservicedomain.events.LixeiraRemoverEvent;
import br.com.todeschini.lixeiraservicedomain.lixeira.spi.LixeiraOperations;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LixeiraEventListener {

    private final LixeiraOperations lixeiraOperations;

    @EventListener
    public <T> void handleIncluirEvent(LixeiraIncluirEvent<T> event) throws IllegalAccessException {
        lixeiraOperations.incluir(event.getEntity());
    }

    @EventListener
    public <T> void handleRemoverEvent(LixeiraRemoverEvent<T> event) throws IllegalAccessException {
        lixeiraOperations.remover(event.getEntity());
    }
}

