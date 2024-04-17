package br.com.todeschini.domain.business.packaging.usedplastic.api;

import org.springframework.stereotype.Component;

@Component
public interface UsedPlasticService extends FindUsedPlastic, InsertUsedPlastic, UpdateUsedPlastic, DeleteUsedPlastic, InactivateUsedPlastic,
        FindAllActiveUsedPlasticAndCurrentOne {
}
