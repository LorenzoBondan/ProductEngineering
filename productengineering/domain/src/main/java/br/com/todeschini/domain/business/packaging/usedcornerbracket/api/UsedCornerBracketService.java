package br.com.todeschini.domain.business.packaging.usedcornerbracket.api;

import org.springframework.stereotype.Component;

@Component
public interface UsedCornerBracketService extends FindUsedCornerBracket, InsertUsedCornerBracket, UpdateUsedCornerBracket, DeleteUsedCornerBracket, InactivateUsedCornerBracket,
        FindAllActiveUsedCornerBracketAndCurrentOne {
}
