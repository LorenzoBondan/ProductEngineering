package br.com.todeschini.domain.business.publico.attachment.api;

import br.com.todeschini.domain.business.publico.attachment.DAttachment;

import java.util.List;

public interface FindAllActiveAttachmentAndCurrentOne {

    List<DAttachment> findAllActiveAndCurrentOne (Long id);
}
