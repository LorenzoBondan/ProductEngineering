package br.com.todeschini.persistence.configurator.configurators.mdpconfigurator;

import br.com.todeschini.domain.business.configurator.configurators.mdpconfigurator.DMDPConfigurator;
import br.com.todeschini.domain.business.configurator.configurators.mdpconfigurator.spi.MDPConfiguratorMethods;
import br.com.todeschini.domain.business.configurator.generators.fathergenerator.DFatherGenerator;
import br.com.todeschini.domain.business.configurator.generators.fathergenerator.api.FatherGeneratorService;
import br.com.todeschini.domain.business.configurator.generators.ghostgenerator.DGhostGenerator;
import br.com.todeschini.domain.business.configurator.generators.ghostgenerator.api.GhostGeneratorService;
import br.com.todeschini.domain.business.configurator.generators.guidegenerator.DGuideGenerator;
import br.com.todeschini.domain.business.configurator.generators.guidegenerator.api.GuideGeneratorService;
import br.com.todeschini.domain.business.configurator.generators.songenerator.DSonGenerator;
import br.com.todeschini.domain.business.configurator.generators.songenerator.api.SonGeneratorService;
import br.com.todeschini.domain.business.configurator.structs.mdpitem.DMDPItem;
import br.com.todeschini.domain.business.configurator.structs.mdpitem.api.MDPItemService;
import br.com.todeschini.domain.business.publico.father.DFather;
import br.com.todeschini.domain.configurator.BPConfigurator;
import br.com.todeschini.domain.configurator.BPConfiguratorSon;
import br.com.todeschini.domain.configurator.ColorConfigurator;
import br.com.todeschini.persistence.entities.mdp.MDPSon;
import br.com.todeschini.persistence.entities.publico.Color;
import br.com.todeschini.persistence.entities.publico.Father;
import br.com.todeschini.persistence.publico.color.ColorRepository;
import br.com.todeschini.persistence.publico.father.FatherDomainToEntityAdapter;
import br.com.todeschini.persistence.publico.father.FatherRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MDPConfiguratorMethodsImpl implements MDPConfiguratorMethods {

    private final FatherRepository fatherRepository;
    private final ColorRepository colorRepository;
    private final MDPItemService mdpItemService;
    private final GhostGeneratorService ghostGeneratorService;
    private final GuideGeneratorService guideGeneratorService;
    private final FatherGeneratorService fatherGeneratorService;
    private final SonGeneratorService sonGeneratorService;
    private final FatherDomainToEntityAdapter fatherAdapter;

    public MDPConfiguratorMethodsImpl(FatherRepository fatherRepository, ColorRepository colorRepository, MDPItemService mdpItemService, GhostGeneratorService ghostGeneratorService, GuideGeneratorService guideGeneratorService, FatherGeneratorService fatherGeneratorService, SonGeneratorService sonGeneratorService, FatherDomainToEntityAdapter fatherAdapter) {
        this.fatherRepository = fatherRepository;
        this.colorRepository = colorRepository;
        this.mdpItemService = mdpItemService;
        this.ghostGeneratorService = ghostGeneratorService;
        this.guideGeneratorService = guideGeneratorService;
        this.fatherGeneratorService = fatherGeneratorService;
        this.sonGeneratorService = sonGeneratorService;
        this.fatherAdapter = fatherAdapter;
    }

    @Override
    @Transactional
    public List<DFather> generateMDPStruct(DMDPConfigurator configurator) {
        List<Father> fatherList = generateMDPFatherStruct(configurator.getConfig(), configurator.getGhostSuffix(), configurator.getImplementation());
        for(Father father : fatherList){

            List<MDPSon> MDPSons = father.getSons().stream()
                    .filter(son -> son instanceof MDPSon)
                    .map(son -> (MDPSon) son)
                    .toList();
            mdpItemService.generateMDPStruct(new DMDPItem((List<Object>) (List<?>) MDPSons, configurator.getEdgeLength(), configurator.getEdgeWidth(), configurator.getGlueCode()));

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

    private List<Father> generateMDPFatherStruct(BPConfigurator config, String ghostSuffix, LocalDate implementation){
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

                MDPSon son = (MDPSon) sonGeneratorService.createOrUpdateMDPSon(new DSonGenerator(
                        Long.parseLong(currentSonCode + color.getCode().toString()),
                        item.getDescription(),
                        color,
                        father,
                        null
                ));
                son.setImplementation(implementation);
                guideGeneratorService.generateGuideSon(new DGuideGenerator(son, item.getMachinesIds(), implementation, LocalDate.parse("9999-12-31")));

                if (!fatherList.contains(father)) {
                    fatherList.add(father);
                }
            }
        }
        return fatherList;
    }
}
