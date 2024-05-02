package br.com.todeschini.persistence.configurator.configurators.mdfconfigurator;

import br.com.todeschini.domain.business.configurator.configurators.mdfconfigurator.DMDFConfigurator;
import br.com.todeschini.domain.business.configurator.configurators.mdfconfigurator.spi.MDFConfiguratorMethods;
import br.com.todeschini.domain.business.configurator.generators.fathergenerator.DFatherGenerator;
import br.com.todeschini.domain.business.configurator.generators.fathergenerator.api.FatherGeneratorService;
import br.com.todeschini.domain.business.configurator.generators.ghostgenerator.DGhostGenerator;
import br.com.todeschini.domain.business.configurator.generators.ghostgenerator.api.GhostGeneratorService;
import br.com.todeschini.domain.business.configurator.generators.guidegenerator.DGuideGenerator;
import br.com.todeschini.domain.business.configurator.generators.guidegenerator.api.GuideGeneratorService;
import br.com.todeschini.domain.business.configurator.generators.songenerator.DSonGenerator;
import br.com.todeschini.domain.business.configurator.generators.songenerator.api.SonGeneratorService;
import br.com.todeschini.domain.business.configurator.structs.mdfitem.DMDFItem;
import br.com.todeschini.domain.business.configurator.structs.mdfitem.api.MDFItemService;
import br.com.todeschini.domain.business.publico.father.DFather;
import br.com.todeschini.domain.configurator.BPConfigurator;
import br.com.todeschini.domain.configurator.BPConfiguratorSon;
import br.com.todeschini.domain.configurator.ColorConfigurator;
import br.com.todeschini.persistence.entities.mdf.PaintingSon;
import br.com.todeschini.persistence.entities.publico.Color;
import br.com.todeschini.persistence.entities.publico.Father;
import br.com.todeschini.persistence.publico.color.ColorRepository;
import br.com.todeschini.persistence.publico.father.FatherDomainToEntityAdapter;
import br.com.todeschini.persistence.publico.father.FatherRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MDFConfiguratorMethodsImpl implements MDFConfiguratorMethods {

    private final ColorRepository colorRepository;
    private final MDFItemService mdfItemService;
    private final GhostGeneratorService ghostGeneratorService;
    private final GuideGeneratorService guideGeneratorService;
    private final FatherGeneratorService fatherGeneratorService;
    private final SonGeneratorService sonGeneratorService;
    private final FatherDomainToEntityAdapter fatherAdapter;
    private final FatherRepository fatherRepository;

    public MDFConfiguratorMethodsImpl(ColorRepository colorRepository, MDFItemService mdfItemService, GhostGeneratorService ghostGeneratorService, GuideGeneratorService guideGeneratorService, FatherGeneratorService fatherGeneratorService, SonGeneratorService sonGeneratorService, FatherDomainToEntityAdapter fatherAdapter, FatherRepository fatherRepository) {
        this.colorRepository = colorRepository;
        this.mdfItemService = mdfItemService;
        this.ghostGeneratorService = ghostGeneratorService;
        this.guideGeneratorService = guideGeneratorService;
        this.fatherGeneratorService = fatherGeneratorService;
        this.sonGeneratorService = sonGeneratorService;
        this.fatherAdapter = fatherAdapter;
        this.fatherRepository = fatherRepository;
    }

    @Override
    @Transactional
    public List<DFather> generateMDFStruct(DMDFConfigurator configurator) {
        List<Father> fatherList = generateMDFFatherStruct(configurator.getConfig(), configurator.getGhostSuffix(), configurator.getImplementation());
        for(Father father : fatherList){

            List<PaintingSon> paintingSons = father.getSons().stream()
                    .filter(son -> son instanceof PaintingSon)
                    .map(son -> (PaintingSon) son)
                    .toList();
            mdfItemService.generateMDFStruct(new DMDFItem((List<Object>) (List<?>) paintingSons, configurator.getSatin(), configurator.getHighShine(), configurator.getSatinGlass(),
                    configurator.getFaces(), configurator.getSpecial(), configurator.getPaintingBorderBackgroundCode(), configurator.getPolyesterCode()));

            ghostGeneratorService.generateGhostStruct(new DGhostGenerator(father.getGhost(),
                    configurator.getCornerBracketCode(), configurator.getPlasticCode(), configurator.getNonwovenFabricCode(), configurator.getPolyethyleneCode(),
                    configurator.getUpper(), configurator.getAdditional(), configurator.getWidth(),
                    configurator.getQuantity(),
                    configurator.getOneFace())
            );

            father.calculateValue();
            father.setImplementation(configurator.getImplementation());
            fatherRepository.save(father);
        }
        return fatherList.stream().map(fatherAdapter::toDomain).collect(Collectors.toList());
    }

    private List<Father> generateMDFFatherStruct(BPConfigurator config, String ghostSuffix, LocalDate implementation){
        List<Father> fatherList = new ArrayList<>();
        for(BPConfiguratorSon item : config.getItems()){
            for (ColorConfigurator colorConfigurator : config.getColors()) {
                Color color = colorRepository.findById(colorConfigurator.getCode()).get();

                Father father = (Father) fatherGeneratorService.createOrUpdateFather( new DFatherGenerator(
                        Long.parseLong(item.getFatherCode().toString() + color.getCode().toString()),
                        item.getDescription(),
                        color));
                ghostGeneratorService.generateGhost(ghostSuffix, father);

                Long currentSonCode = Long.parseLong(item.getSonCode().toString());
                fatherGeneratorService.addAttachment(currentSonCode, father);

                PaintingSon paintingSon = (PaintingSon) sonGeneratorService.createOrUpdatePaintingSon( new DSonGenerator(
                        Long.parseLong(currentSonCode + color.getCode().toString()),
                        item.getDescription(),
                        color,
                        father,
                        null
                ));
                guideGeneratorService.generateGuideSon(new DGuideGenerator(paintingSon, item.getMachinesIds(), implementation, LocalDate.parse("9999-12-31")));

                if (!fatherList.contains(father)) {
                    fatherList.add(father);
                }
            }
        }
        return fatherList;
    }
}
