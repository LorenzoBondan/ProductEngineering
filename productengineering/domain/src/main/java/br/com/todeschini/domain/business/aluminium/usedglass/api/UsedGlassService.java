package br.com.todeschini.domain.business.aluminium.usedglass.api;

import org.springframework.stereotype.Component;

@Component
public interface UsedGlassService extends FindUsedGlass, InsertUsedGlass, UpdateUsedGlass, DeleteUsedGlass, InactivateUsedGlass,
        FindAllActiveUsedGlassAndCurrentOne {
}
