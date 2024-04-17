package br.com.todeschini.persistence.mdf.paintingson;

import br.com.todeschini.domain.business.mdf.paintingson.DPaintingSon;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.mdf.PaintingSon;
import br.com.todeschini.persistence.entities.publico.Father;
import br.com.todeschini.persistence.guides.guide.GuideDomainToEntityAdapter;
import br.com.todeschini.persistence.guides.guide.GuideRepository;
import br.com.todeschini.persistence.mdf.back.BackDomainToEntityAdapter;
import br.com.todeschini.persistence.mdf.back.BackRepository;
import br.com.todeschini.persistence.mdf.usedpainting.UsedPaintingDomainToEntityAdapter;
import br.com.todeschini.persistence.mdf.usedpainting.UsedPaintingRepository;
import br.com.todeschini.persistence.mdf.usedpaintingborderbackground.UsedPaintingBorderBackgroundDomainToEntityAdapter;
import br.com.todeschini.persistence.mdf.usedpaintingborderbackground.UsedPaintingBorderBackgroundRepository;
import br.com.todeschini.persistence.mdf.usedpolyester.UsedPolyesterDomainToEntityAdapter;
import br.com.todeschini.persistence.mdf.usedpolyester.UsedPolyesterRepository;
import br.com.todeschini.persistence.publico.color.ColorDomainToEntityAdapter;
import br.com.todeschini.persistence.publico.color.ColorRepository;
import br.com.todeschini.persistence.publico.item.ItemRepository;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PaintingSonDomainToEntityAdapter implements Convertable<PaintingSon, DPaintingSon> {

    private final ItemRepository itemRepository;
    private final ColorRepository colorRepository;
    private final ColorDomainToEntityAdapter colorAdapter;
    private final GuideRepository guideRepository;
    private final GuideDomainToEntityAdapter guideAdapter;
    private final BackRepository backRepository;
    private final BackDomainToEntityAdapter backAdapter;
    private final UsedPaintingRepository usedPaintingRepository;
    private final UsedPaintingDomainToEntityAdapter usedPaintingAdapter;
    private final UsedPaintingBorderBackgroundRepository usedPaintingBorderBackgroundRepository;
    private final UsedPaintingBorderBackgroundDomainToEntityAdapter usedPaintingBorderBackgroundAdapter;
    private final UsedPolyesterRepository usedPolyesterRepository;
    private final UsedPolyesterDomainToEntityAdapter usedPolyesterAdapter;

    public PaintingSonDomainToEntityAdapter(ItemRepository itemRepository,
                                            ColorRepository colorRepository, ColorDomainToEntityAdapter colorAdapter,
                                            GuideRepository guideRepository, GuideDomainToEntityAdapter guideAdapter,
                                            BackRepository backRepository, BackDomainToEntityAdapter backAdapter,
                                            UsedPaintingRepository usedPaintingRepository, UsedPaintingDomainToEntityAdapter usedPaintingAdapter,
                                            UsedPaintingBorderBackgroundRepository usedPaintingBorderBackgroundRepository, UsedPaintingBorderBackgroundDomainToEntityAdapter usedPaintingBorderBackgroundAdapter,
                                            UsedPolyesterRepository usedPolyesterRepository, UsedPolyesterDomainToEntityAdapter usedPolyesterAdapter) {
        this.itemRepository = itemRepository;
        this.colorRepository = colorRepository;
        this.colorAdapter = colorAdapter;
        this.guideRepository = guideRepository;
        this.guideAdapter = guideAdapter;
        this.backRepository = backRepository;
        this.backAdapter = backAdapter;
        this.usedPaintingRepository = usedPaintingRepository;
        this.usedPaintingAdapter = usedPaintingAdapter;
        this.usedPaintingBorderBackgroundRepository = usedPaintingBorderBackgroundRepository;
        this.usedPaintingBorderBackgroundAdapter = usedPaintingBorderBackgroundAdapter;
        this.usedPolyesterRepository = usedPolyesterRepository;
        this.usedPolyesterAdapter = usedPolyesterAdapter;
    }

    @Override
    public PaintingSon toEntity(DPaintingSon domain) {
        PaintingSon entity = new PaintingSon();
        entity.setCode(domain.getCode());
        entity.setDescription(domain.getDescription());
        entity.setMeasure1(domain.getMeasure1());
        entity.setMeasure2(domain.getMeasure2());
        entity.setMeasure3(domain.getMeasure3());
        //entity.setMeasurementUnit(domain.getMeasurementUnit());
        entity.setSatin(domain.getSatin());
        entity.setSatinGlass(domain.getSatinGlass());
        entity.setHighShine(domain.getHighShine());
        entity.setSpecial(domain.getSpecial());
        entity.setSuffix(domain.getSuffix());
        entity.setFaces(domain.getFaces());
        entity.setColor(colorRepository.findById(domain.getColor().getCode()).get());
        if(!itemRepository.existsById(domain.getFatherCode())){
            throw new ResourceNotFoundException("Código do pai não encontrado: " + domain.getFatherCode());
        }
        entity.setFather(new Father(domain.getFatherCode()));
        if(domain.getGuide() != null){
            entity.setGuide(guideRepository.findById(domain.getGuide().getId()).get());
        }
        if(domain.getBack() != null){
            entity.setBack(backRepository.findById(domain.getBack().getCode()).get());
        }
        entity.setPaintings(domain.getPaintings().stream().map(painting -> usedPaintingRepository.findById(painting.getId()).get()).collect(Collectors.toList()));
        entity.setPaintingBorderBackgrounds(domain.getPaintingBorderBackgrounds().stream().map(paintingBorderBackground -> usedPaintingBorderBackgroundRepository.findById(paintingBorderBackground.getId()).get()).collect(Collectors.toList()));
        entity.setPolyesters(domain.getPolyesters().stream().map(polyester -> usedPolyesterRepository.findById(polyester.getId()).get()).collect(Collectors.toList()));

        return entity;
    }

    @Override
    public DPaintingSon toDomain(PaintingSon entity) {
        DPaintingSon domain = new DPaintingSon();
        domain.setCode(entity.getCode());
        domain.setDescription(entity.getDescription());
        domain.setMeasure1(entity.getMeasure1());
        domain.setMeasure2(entity.getMeasure2());
        domain.setMeasure3(entity.getMeasure3());
        domain.setMeasurementUnit(entity.getMeasurementUnit());
        domain.setSatin(entity.getSatin());
        domain.setSatinGlass(entity.getSatinGlass());
        domain.setHighShine(entity.getHighShine());
        domain.setSpecial(entity.getSpecial());
        domain.setSuffix(entity.getSuffix());
        domain.setFaces(entity.getFaces());
        domain.setColor(colorAdapter.toDomain(entity.getColor()));
        domain.setFatherCode(entity.getFather().getCode());
        if(entity.getGuide() != null){
            domain.setGuide(guideAdapter.toDomain(entity.getGuide()));
        }
        if(entity.getBack() != null){
            domain.setBack(backAdapter.toDomain(entity.getBack()));
        }
        domain.setPaintings(entity.getPaintings().stream().map(usedPaintingAdapter::toDomain).collect(Collectors.toList()));
        domain.setPaintingBorderBackgrounds(entity.getPaintingBorderBackgrounds().stream().map(usedPaintingBorderBackgroundAdapter::toDomain).collect(Collectors.toList()));
        domain.setPolyesters(entity.getPolyesters().stream().map(usedPolyesterAdapter::toDomain).collect(Collectors.toList()));

        return domain;
    }
}
