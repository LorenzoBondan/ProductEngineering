package br.com.todeschini.domain.business.packaging.plastic.api;

import org.springframework.stereotype.Component;

@Component
public interface PlasticService extends FindPlastic, InsertPlastic, UpdatePlastic, DeletePlastic, InactivatePlastic,
        FindAllActivePlasticAndCurrentOne {
}
