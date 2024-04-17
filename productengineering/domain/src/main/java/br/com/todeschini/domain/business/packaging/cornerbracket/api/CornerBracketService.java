package br.com.todeschini.domain.business.packaging.cornerbracket.api;

import org.springframework.stereotype.Component;

@Component
public interface CornerBracketService extends FindCornerBracket, InsertCornerBracket, UpdateCornerBracket, DeleteCornerBracket, InactivateCornerBracket,
        FindAllActiveCornerBracketAndCurrentOne {
}
