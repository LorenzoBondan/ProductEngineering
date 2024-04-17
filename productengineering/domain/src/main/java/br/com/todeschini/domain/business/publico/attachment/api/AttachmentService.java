package br.com.todeschini.domain.business.publico.attachment.api;

import org.springframework.stereotype.Component;

@Component
public interface AttachmentService extends FindAttachment, InsertAttachment, UpdateAttachment, DeleteAttachment, InactivateAttachment,
        FindAllActiveAttachmentAndCurrentOne {
}
