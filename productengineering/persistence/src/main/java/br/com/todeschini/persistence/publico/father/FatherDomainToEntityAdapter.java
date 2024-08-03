package br.com.todeschini.persistence.publico.father;

import br.com.todeschini.domain.business.enums.DStatus;
import br.com.todeschini.domain.business.publico.father.DFather;
import br.com.todeschini.persistence.entities.publico.Father;
import br.com.todeschini.persistence.packaging.ghost.GhostDomainToEntityAdapter;
import br.com.todeschini.persistence.packaging.ghost.GhostRepository;
import br.com.todeschini.persistence.publico.attachment.AttachmentDomainToEntityAdapter;
import br.com.todeschini.persistence.publico.attachment.AttachmentRepository;
import br.com.todeschini.persistence.publico.color.ColorDomainToEntityAdapter;
import br.com.todeschini.persistence.publico.color.ColorRepository;
import br.com.todeschini.persistence.publico.son.SonDomainToEntityAdapter;
import br.com.todeschini.persistence.publico.son.SonRepository;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class FatherDomainToEntityAdapter implements Convertable<Father, DFather> {

    private final ColorRepository colorRepository;
    private final ColorDomainToEntityAdapter colorAdapter;
    private final SonRepository sonRepository;
    private final SonDomainToEntityAdapter sonAdapter;
    private final GhostRepository ghostRepository;
    private final GhostDomainToEntityAdapter ghostAdapter;
    private final AttachmentRepository attachmentRepository;
    private final AttachmentDomainToEntityAdapter attachmentAdapter;

    public FatherDomainToEntityAdapter(ColorRepository colorRepository, ColorDomainToEntityAdapter colorAdapter,
                                       SonRepository sonRepository, SonDomainToEntityAdapter sonAdapter,
                                       GhostRepository ghostRepository, GhostDomainToEntityAdapter ghostAdapter,
                                       AttachmentRepository attachmentRepository, AttachmentDomainToEntityAdapter attachmentAdapter) {
        this.colorRepository = colorRepository;
        this.colorAdapter = colorAdapter;
        this.sonRepository = sonRepository;
        this.sonAdapter = sonAdapter;
        this.ghostRepository = ghostRepository;
        this.ghostAdapter = ghostAdapter;
        this.attachmentRepository = attachmentRepository;
        this.attachmentAdapter = attachmentAdapter;
    }

    @Override
    public Father toEntity(DFather domain) {
        Father entity = new Father();
        entity.setCode(domain.getCode());
        entity.setDescription(domain.getDescription().toUpperCase());
        entity.setMeasure1(domain.getMeasure1());
        entity.setMeasure2(domain.getMeasure2());
        entity.setMeasure3(domain.getMeasure3());
        entity.setImplementation(domain.getImplementation());
        //entity.setMeasurementUnit(domain.getMeasurementUnit());
        entity.setColor(colorRepository.findById(domain.getColor().getCode()).get());
        if(domain.getGhost() != null){
            entity.setGhost(ghostRepository.findByCode(domain.getGhost().getCode()));
        }
        entity.setSons(domain.getSons().stream().map(son -> sonRepository.findById(son.getCode()).get()).collect(Collectors.toList()));
        entity.setAttachments(domain.getAttachments().stream().map(attachment -> attachmentRepository.findById(attachment.getCode()).get()).collect(Collectors.toSet()));
        entity.setValue(domain.getValue());
        return entity;
    }

    @Override
    public DFather toDomain(Father entity) {
        DFather domain = new DFather();
        domain.setCode(entity.getCode());
        domain.setDescription(entity.getDescription().toUpperCase());
        domain.setMeasure1(entity.getMeasure1());
        domain.setMeasure2(entity.getMeasure2());
        domain.setMeasure3(entity.getMeasure3());
        domain.setImplementation(entity.getImplementation());
        domain.setMeasurementUnit(entity.getMeasurementUnit());
        domain.setColor(colorAdapter.toDomain(entity.getColor()));
        if(entity.getGhost() != null){
            domain.setGhost(ghostAdapter.toDomain(entity.getGhost()));
        }
        domain.setSons(entity.getSons().stream().map(sonAdapter::toDomain).collect(Collectors.toList()));
        domain.setAttachments(entity.getAttachments().stream().map(attachmentAdapter::toDomain).collect(Collectors.toList()));
        domain.setValue(Math.round(entity.calculateValue() * 1e2) / 1e2);
        domain.setStatus(DStatus.valueOf(entity.getStatus().name()));
        return domain;
    }
}
