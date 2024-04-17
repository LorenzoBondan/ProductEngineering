package br.com.todeschini.domain.business.mdf.back.api;

import org.springframework.stereotype.Component;

@Component
public interface BackService extends FindBack, InsertBack, UpdateBack, DeleteBack, InactivateBack,
        FindAllActiveBackAndCurrentOne {
}
