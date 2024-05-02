package br.com.todeschini.persistence.aluminium.aluminiumson;

import br.com.todeschini.domain.business.aluminium.aluminiumson.DAluminiumSon;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.aluminium.aluminiumtype.AluminiumTypeDomainToEntityAdapter;
import br.com.todeschini.persistence.aluminium.aluminiumtype.AluminiumTypeRepository;
import br.com.todeschini.persistence.aluminium.useddrawerpull.UsedDrawerPullDomainToEntityAdapter;
import br.com.todeschini.persistence.aluminium.useddrawerpull.UsedDrawerPullRepository;
import br.com.todeschini.persistence.aluminium.usedglass.UsedGlassDomainToEntityAdapter;
import br.com.todeschini.persistence.aluminium.usedglass.UsedGlassRepository;
import br.com.todeschini.persistence.aluminium.usedmolding.UsedMoldingDomainToEntityAdapter;
import br.com.todeschini.persistence.aluminium.usedmolding.UsedMoldingRepository;
import br.com.todeschini.persistence.aluminium.usedscrew.UsedScrewDomainToEntityAdapter;
import br.com.todeschini.persistence.aluminium.usedscrew.UsedScrewRepository;
import br.com.todeschini.persistence.aluminium.usedtrysquare.UsedTrySquareDomainToEntityAdapter;
import br.com.todeschini.persistence.aluminium.usedtrysquare.UsedTrySquareRepository;
import br.com.todeschini.persistence.entities.aluminium.AluminiumSon;
import br.com.todeschini.persistence.entities.publico.Father;
import br.com.todeschini.persistence.guides.guide.GuideDomainToEntityAdapter;
import br.com.todeschini.persistence.guides.guide.GuideRepository;
import br.com.todeschini.persistence.mdp.mdpson.MDPSonDomainToEntityAdapter;
import br.com.todeschini.persistence.mdp.mdpson.MDPSonRepository;
import br.com.todeschini.persistence.publico.color.ColorDomainToEntityAdapter;
import br.com.todeschini.persistence.publico.color.ColorRepository;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AluminiumSonDomainToEntityAdapter implements Convertable<AluminiumSon, DAluminiumSon> {

    private final ColorRepository colorRepository;
    private final ColorDomainToEntityAdapter colorAdapter;
    private final GuideRepository guideRepository;
    private final GuideDomainToEntityAdapter guideAdapter;
    private final AluminiumTypeRepository aluminiumTypeRepository;
    private final AluminiumTypeDomainToEntityAdapter aluminiumTypeAdapter;
    private final MDPSonRepository mdpsonRepository;
    private final MDPSonDomainToEntityAdapter mdpSonAdapter;
    private final UsedDrawerPullRepository usedDrawerPullRepository;
    private final UsedDrawerPullDomainToEntityAdapter usedDrawerPullAdapter;
    private final UsedTrySquareRepository usedTrySquareRepository;
    private final UsedTrySquareDomainToEntityAdapter usedTrySquareAdapter;
    private final UsedScrewRepository usedScrewRepository;
    private final UsedScrewDomainToEntityAdapter usedScrewAdapter;
    private final UsedMoldingRepository usedMoldingRepository;
    private final UsedMoldingDomainToEntityAdapter usedMoldingAdapter;
    private final UsedGlassRepository usedGlassRepository;
    private final UsedGlassDomainToEntityAdapter usedGlassAdapter;

    public AluminiumSonDomainToEntityAdapter(ColorRepository colorRepository, ColorDomainToEntityAdapter colorAdapter,
                                             GuideRepository guideRepository, GuideDomainToEntityAdapter guideAdapter,
                                             AluminiumTypeRepository aluminiumTypeRepository, AluminiumTypeDomainToEntityAdapter aluminiumTypeAdapter,
                                             MDPSonRepository mdpsonRepository, MDPSonDomainToEntityAdapter mdpSonAdapter,
                                             UsedDrawerPullRepository usedDrawerPullRepository, UsedDrawerPullDomainToEntityAdapter usedDrawerPullAdapter,
                                             UsedTrySquareRepository usedTrySquareRepository, UsedTrySquareDomainToEntityAdapter usedTrySquareAdapter,
                                             UsedScrewRepository usedScrewRepository, UsedScrewDomainToEntityAdapter usedScrewAdapter,
                                             UsedMoldingRepository usedMoldingRepository, UsedMoldingDomainToEntityAdapter usedMoldingAdapter,
                                             UsedGlassRepository usedGlassRepository, UsedGlassDomainToEntityAdapter usedGlassAdapter
                                             ) {
        this.colorRepository = colorRepository;
        this.colorAdapter = colorAdapter;
        this.guideRepository = guideRepository;
        this.guideAdapter = guideAdapter;
        this.aluminiumTypeRepository = aluminiumTypeRepository;
        this.aluminiumTypeAdapter = aluminiumTypeAdapter;
        this.mdpsonRepository = mdpsonRepository;
        this.mdpSonAdapter = mdpSonAdapter;
        this.usedDrawerPullRepository = usedDrawerPullRepository;
        this.usedDrawerPullAdapter = usedDrawerPullAdapter;
        this.usedTrySquareRepository = usedTrySquareRepository;
        this.usedTrySquareAdapter = usedTrySquareAdapter;
        this.usedScrewRepository = usedScrewRepository;
        this.usedScrewAdapter = usedScrewAdapter;
        this.usedMoldingRepository = usedMoldingRepository;
        this.usedMoldingAdapter = usedMoldingAdapter;
        this.usedGlassRepository = usedGlassRepository;
        this.usedGlassAdapter = usedGlassAdapter;
    }

    @Override
    public AluminiumSon toEntity(DAluminiumSon domain) {
        AluminiumSon entity = new AluminiumSon();
        entity.setCode(domain.getCode());
        entity.setDescription(domain.getDescription());
        entity.setMeasure1(domain.getMeasure1());
        entity.setMeasure2(domain.getMeasure2());
        entity.setMeasure3(domain.getMeasure3());
        entity.setImplementation(domain.getImplementation());
        //entity.setMeasurementUnit(domain.getMeasurementUnit());
        entity.setColor(colorRepository.findById(domain.getColor().getCode()).orElseThrow(() -> new ResourceNotFoundException("Cor não encontrada")));
        entity.setFather(new Father(domain.getFatherCode()));
        if(domain.getGuide() != null){
            entity.setGuide(guideRepository.findById(domain.getGuide().getId()).orElse(null));
        }
        if(domain.getAluminiumType() != null){
            entity.setAluminiumType(aluminiumTypeRepository.findById(domain.getAluminiumType().getId()).orElseThrow(() -> new ResourceNotFoundException("Tipo de Alumínio não encontrado")));
        }
        if(domain.getSons() != null){
            entity.setSons(domain.getSons().stream().map(son -> mdpsonRepository.findById(son.getCode()).orElse(null)).collect(Collectors.toList()));
        }
        if(domain.getDrawerPull() != null){
            entity.setDrawerPull(usedDrawerPullRepository.findById(domain.getDrawerPull().getId()).orElse(null));
        }
        if(domain.getTrySquares() != null){
            entity.setTrySquares(domain.getTrySquares().stream().map(trySquare -> usedTrySquareRepository.findById(trySquare.getId()).orElse(null)).collect(Collectors.toList()));
        }
        if(domain.getScrews() != null){
            entity.setScrews(domain.getScrews().stream().map(screw -> usedScrewRepository.findById(screw.getId()).orElse(null)).collect(Collectors.toList()));
        }
        if(domain.getMoldings() != null){
            entity.setMoldings(domain.getMoldings().stream().map(molding -> usedMoldingRepository.findById(molding.getId()).orElse(null)).collect(Collectors.toList()));
        }
        if(domain.getGlass() != null){
            entity.setGlass(usedGlassRepository.findById(domain.getGlass().getId()).orElse(null));
        }

        return entity;
    }

    @Override
    public DAluminiumSon toDomain(AluminiumSon entity) {
        DAluminiumSon domain = new DAluminiumSon();
        domain.setCode(entity.getCode());
        domain.setDescription(entity.getDescription());
        domain.setMeasure1(entity.getMeasure1());
        domain.setMeasure2(entity.getMeasure2());
        domain.setMeasure3(entity.getMeasure3());
        domain.setImplementation(entity.getImplementation());
        domain.setMeasurementUnit(entity.getMeasurementUnit());
        domain.setColor(colorAdapter.toDomain(entity.getColor()));
        domain.setFatherCode(entity.getFather().getCode());
        if(entity.getGuide() != null){
            domain.setGuide(guideAdapter.toDomain(entity.getGuide()));
        }
        if(entity.getAluminiumType() != null){
            domain.setAluminiumType(aluminiumTypeAdapter.toDomain(entity.getAluminiumType()));
        }
        if(entity.getSons() != null){
            domain.setSons(entity.getSons().stream().map(mdpSonAdapter::toDomain).collect(Collectors.toList()));
        }
        if(entity.getDrawerPull() != null){
            domain.setDrawerPull(usedDrawerPullAdapter.toDomain(entity.getDrawerPull()));
        }
        if(entity.getTrySquares() != null){
            domain.setTrySquares(entity.getTrySquares().stream().map(usedTrySquareAdapter::toDomain).collect(Collectors.toList()));
        }
        if(entity.getScrews() != null){
            domain.setScrews(entity.getScrews().stream().map(usedScrewAdapter::toDomain).collect(Collectors.toList()));
        }
        if(entity.getMoldings() != null){
            domain.setMoldings(entity.getMoldings().stream().map(usedMoldingAdapter::toDomain).collect(Collectors.toList()));
        }
        if(entity.getGlass() != null){
            domain.setGlass(usedGlassAdapter.toDomain(entity.getGlass()));
        }

        domain.setValue(Math.round(entity.calculateValue() * 1e2) / 1e2);
        return domain;
    }
}
