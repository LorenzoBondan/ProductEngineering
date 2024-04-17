package br.com.todeschini.domain.business.publico.color.api;

import org.springframework.stereotype.Component;

@Component
public interface ColorService extends FindColor, InsertColor, UpdateColor, DeleteColor, InactivateColor,
        FindAllActiveColorAndCurrentOne {
}
