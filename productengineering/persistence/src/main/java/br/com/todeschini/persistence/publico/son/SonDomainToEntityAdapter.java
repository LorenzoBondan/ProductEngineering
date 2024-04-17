package br.com.todeschini.persistence.publico.son;

import br.com.todeschini.domain.business.publico.son.DSon;
import br.com.todeschini.persistence.entities.publico.Father;
import br.com.todeschini.persistence.entities.publico.Son;
import br.com.todeschini.persistence.guides.guide.GuideDomainToEntityAdapter;
import br.com.todeschini.persistence.guides.guide.GuideRepository;
import br.com.todeschini.persistence.publico.color.ColorDomainToEntityAdapter;
import br.com.todeschini.persistence.publico.color.ColorRepository;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class SonDomainToEntityAdapter implements Convertable<Son, DSon> {

    private final ColorRepository colorRepository;
    private final ColorDomainToEntityAdapter colorAdapter;
    private final GuideRepository guideRepository;
    private final GuideDomainToEntityAdapter guideAdapter;

    public SonDomainToEntityAdapter(ColorRepository colorRepository, ColorDomainToEntityAdapter colorAdapter,
                                    GuideRepository guideRepository, GuideDomainToEntityAdapter guideAdapter) {
        this.colorRepository = colorRepository;
        this.colorAdapter = colorAdapter;
        this.guideRepository = guideRepository;
        this.guideAdapter = guideAdapter;
    }

    @Override
    public Son toEntity(DSon domain) {
        Son entity = new Son();
        entity.setCode(domain.getCode());
        entity.setDescription(domain.getDescription());
        entity.setMeasure1(domain.getMeasure1());
        entity.setMeasure2(domain.getMeasure2());
        entity.setMeasure3(domain.getMeasure3());
        //entity.setMeasurementUnit(domain.getMeasurementUnit());
        entity.setColor(colorRepository.findById(domain.getColor().getCode()).get());
        entity.setFather(new Father(domain.getFatherCode()));
        if(domain.getGuide() != null){
            entity.setGuide(guideRepository.findById(domain.getGuide().getId()).get());
        }
        return entity;
    }

    @Override
    public DSon toDomain(Son entity) {
        DSon domain = new DSon();
        domain.setCode(entity.getCode());
        domain.setDescription(entity.getDescription());
        domain.setMeasure1(entity.getMeasure1());
        domain.setMeasure2(entity.getMeasure2());
        domain.setMeasure3(entity.getMeasure3());
        domain.setMeasurementUnit(entity.getMeasurementUnit());
        domain.setColor(colorAdapter.toDomain(entity.getColor()));
        domain.setFatherCode(entity.getFather().getCode());
        if(entity.getGuide() != null){
            domain.setGuide(guideAdapter.toDomain(entity.getGuide()));
        }
        return domain;
    }
}
