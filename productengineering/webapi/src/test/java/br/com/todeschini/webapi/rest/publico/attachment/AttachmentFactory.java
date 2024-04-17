package br.com.todeschini.webapi.rest.publico.attachment;

import br.com.todeschini.domain.business.publico.attachment.DAttachment;

public class AttachmentFactory {

    public static DAttachment createDAttachment() {
        DAttachment attachment = new DAttachment();
        attachment.setCode(1L);
        attachment.setDescription("Attachment");
        attachment.setMeasure1(1);
        attachment.setMeasure2(1);
        attachment.setMeasure3(1);
        attachment.setMeasurementUnit("UN");
        return attachment;
    }

    public static DAttachment createDuplicatedDAttachment() {
        DAttachment attachment = new DAttachment();
        attachment.setCode(2L);
        attachment.setDescription("Attachment");
        attachment.setMeasure1(1);
        attachment.setMeasure2(1);
        attachment.setMeasure3(1);
        attachment.setMeasurementUnit("UN");
        return attachment;
    }

    public static DAttachment createNonExistingDAttachment(Long nonExistingId) {
        DAttachment attachment = new DAttachment();
        attachment.setCode(nonExistingId);
        attachment.setDescription("Attachment");
        attachment.setMeasure1(1);
        attachment.setMeasure2(1);
        attachment.setMeasure3(1);
        attachment.setMeasurementUnit("UN");
        return attachment;
    }
}
