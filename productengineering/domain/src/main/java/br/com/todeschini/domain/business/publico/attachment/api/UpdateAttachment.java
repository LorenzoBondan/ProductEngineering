package br.com.todeschini.domain.business.publico.attachment.api;

import br.com.todeschini.domain.business.publico.attachment.DAttachment;

public interface UpdateAttachment {

    DAttachment update (Long id, DAttachment attachment);
}
