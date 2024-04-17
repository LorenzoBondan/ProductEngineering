package br.com.todeschini.domain.business.aluminium.glass.api;

import org.springframework.stereotype.Component;

@Component
public interface GlassService extends FindGlass, InsertGlass, UpdateGlass, DeleteGlass, InactivateGlass,
        FindAllActiveGlassAndCurrentOne {
}
