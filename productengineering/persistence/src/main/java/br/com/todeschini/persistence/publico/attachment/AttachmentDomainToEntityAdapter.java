package br.com.todeschini.persistence.publico.attachment;

import br.com.todeschini.domain.business.publico.attachment.DAttachment;
import br.com.todeschini.persistence.entities.publico.Attachment;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class AttachmentDomainToEntityAdapter implements Convertable<Attachment, DAttachment> {

    @Override
    public Attachment toEntity(DAttachment domain) {
        Attachment entity = new Attachment();
        entity.setCode(domain.getCode());
        entity.setDescription(domain.getDescription());
        entity.setMeasure1(domain.getMeasure1());
        entity.setMeasure2(domain.getMeasure2());
        entity.setMeasure3(domain.getMeasure3());
        entity.setImplementation(domain.getImplementation());
        entity.setValue(domain.getValue());
        return entity;
    }

    @Override
    public DAttachment toDomain(Attachment entity) {
        DAttachment domain = new DAttachment();
        domain.setCode(entity.getCode());
        domain.setDescription(entity.getDescription());
        domain.setMeasure1(entity.getMeasure1());
        domain.setMeasure2(entity.getMeasure2());
        domain.setMeasure3(entity.getMeasure3());
        domain.setMeasurementUnit(entity.getMeasurementUnit());
        domain.setImplementation(domain.getImplementation());
        domain.setValue(entity.getValue());
        return domain;
    }
}
