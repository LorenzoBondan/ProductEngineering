package br.com.todeschini.persistence.mdp.mdpson;

import br.com.todeschini.domain.business.mdp.mdpson.DMDPSon;
import br.com.todeschini.persistence.entities.mdp.MDPSon;
import br.com.todeschini.persistence.entities.publico.Father;
import br.com.todeschini.persistence.guides.guide.GuideDomainToEntityAdapter;
import br.com.todeschini.persistence.guides.guide.GuideRepository;
import br.com.todeschini.persistence.mdp.usededgebanding.UsedEdgeBandingDomainToEntityAdapter;
import br.com.todeschini.persistence.mdp.usededgebanding.UsedEdgeBandingRepository;
import br.com.todeschini.persistence.mdp.usedglue.UsedGlueDomainToEntityAdapter;
import br.com.todeschini.persistence.mdp.usedglue.UsedGlueRepository;
import br.com.todeschini.persistence.mdp.usedsheet.UsedSheetDomainToEntityAdapter;
import br.com.todeschini.persistence.mdp.usedsheet.UsedSheetRepository;
import br.com.todeschini.persistence.publico.color.ColorDomainToEntityAdapter;
import br.com.todeschini.persistence.publico.color.ColorRepository;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MDPSonDomainToEntityAdapter implements Convertable<MDPSon, DMDPSon> {

    private final ColorRepository colorRepository;
    private final ColorDomainToEntityAdapter colorAdapter;
    private final GuideRepository guideRepository;
    private final GuideDomainToEntityAdapter guideAdapter;
    private final UsedSheetRepository usedSheetRepository;
    private final UsedSheetDomainToEntityAdapter usedSheetAdapter;
    private final UsedEdgeBandingRepository usedEdgeBandingRepository;
    private final UsedEdgeBandingDomainToEntityAdapter usedEdgeBandingAdapter;
    private final UsedGlueRepository usedGlueRepository;
    private final UsedGlueDomainToEntityAdapter usedGlueAdapter;

    public MDPSonDomainToEntityAdapter(ColorRepository colorRepository, ColorDomainToEntityAdapter colorAdapter,
                                       GuideRepository guideRepository, GuideDomainToEntityAdapter guideAdapter,
                                       UsedSheetRepository usedSheetRepository, UsedSheetDomainToEntityAdapter usedSheetAdapter,
                                       UsedEdgeBandingRepository usedEdgeBandingRepository, UsedEdgeBandingDomainToEntityAdapter usedEdgeBandingAdapter,
                                       UsedGlueRepository usedGlueRepository, UsedGlueDomainToEntityAdapter usedGlueAdapter) {
        this.colorRepository = colorRepository;
        this.colorAdapter = colorAdapter;
        this.guideRepository = guideRepository;
        this.guideAdapter = guideAdapter;
        this.usedSheetRepository = usedSheetRepository;
        this.usedSheetAdapter = usedSheetAdapter;
        this.usedEdgeBandingRepository = usedEdgeBandingRepository;
        this.usedEdgeBandingAdapter = usedEdgeBandingAdapter;
        this.usedGlueRepository = usedGlueRepository;
        this.usedGlueAdapter = usedGlueAdapter;
    }

    @Override
    public MDPSon toEntity(DMDPSon domain) {
        MDPSon entity = new MDPSon();
        entity.setCode(domain.getCode());
        entity.setDescription(domain.getDescription());
        entity.setMeasure1(domain.getMeasure1());
        entity.setMeasure2(domain.getMeasure2());
        entity.setMeasure3(domain.getMeasure3());
        entity.setImplementation(domain.getImplementation());
        //entity.setMeasurementUnit(domain.getMeasurementUnit());
        entity.setColor(colorRepository.findById(domain.getColor().getCode()).get());
        if(domain.getFatherCode() != null){
            entity.setFather(new Father(domain.getFatherCode()));
        }
        if(domain.getGuide() != null){
            entity.setGuide(guideRepository.findById(domain.getGuide().getId()).get());
        }

        entity.setSheets(domain.getSheets().stream().map(sheet -> usedSheetRepository.findById(sheet.getId()).get()).collect(Collectors.toSet()));
        entity.setEdgeBandings(domain.getEdgeBandings().stream().map(edgeBanding -> usedEdgeBandingRepository.findById(edgeBanding.getId()).get()).collect(Collectors.toSet()));
        entity.setGlues(domain.getGlues().stream().map(glue -> usedGlueRepository.findById(glue.getId()).get()).collect(Collectors.toSet()));

        return entity;
    }

    @Override
    public DMDPSon toDomain(MDPSon entity) {
        DMDPSon domain = new DMDPSon();
        domain.setCode(entity.getCode());
        domain.setDescription(entity.getDescription());
        domain.setMeasure1(entity.getMeasure1());
        domain.setMeasure2(entity.getMeasure2());
        domain.setMeasure3(entity.getMeasure3());
        domain.setMeasurementUnit(entity.getMeasurementUnit());
        domain.setImplementation(entity.getImplementation());
        domain.setColor(colorAdapter.toDomain(entity.getColor()));
        if(entity.getFather() != null){
            domain.setFatherCode(entity.getFather().getCode());
        }
        if(entity.getGuide() != null){
            domain.setGuide(guideAdapter.toDomain(entity.getGuide()));
        }

        domain.setSheets(entity.getSheets().stream().map(usedSheetAdapter::toDomain).collect(Collectors.toList()));
        domain.setEdgeBandings(entity.getEdgeBandings().stream().map(usedEdgeBandingAdapter::toDomain).collect(Collectors.toList()));
        domain.setGlues(entity.getGlues().stream().map(usedGlueAdapter::toDomain).collect(Collectors.toList()));

        return domain;
    }
}
