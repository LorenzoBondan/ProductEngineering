package br.com.todeschini.persistence.configurator.configurators.modulationconfigurator;

import br.com.todeschini.domain.business.configurator.configurators.modulationconfigurator.DModulationConfigurator;
import br.com.todeschini.domain.business.configurator.configurators.modulationconfigurator.spi.ModulationConfiguratorMethods;
import br.com.todeschini.domain.business.configurator.generators.fathergenerator.DFatherGenerator;
import br.com.todeschini.domain.business.configurator.generators.fathergenerator.api.FatherGeneratorService;
import br.com.todeschini.domain.business.configurator.generators.ghostgenerator.DGhostGenerator;
import br.com.todeschini.domain.business.configurator.generators.ghostgenerator.api.GhostGeneratorService;
import br.com.todeschini.domain.business.configurator.generators.songenerator.api.SonGeneratorService;
import br.com.todeschini.domain.business.configurator.structs.mdpitem.DMDPItem;
import br.com.todeschini.domain.business.configurator.structs.mdpitem.api.MDPItemService;
import br.com.todeschini.domain.business.publico.father.DFather;
import br.com.todeschini.domain.configurator.ColorConfigurator;
import br.com.todeschini.domain.configurator.ModulationConfigurator;
import br.com.todeschini.domain.configurator.ModulationSonConfigurator;
import br.com.todeschini.domain.configurator.SonConfigurator;
import br.com.todeschini.persistence.entities.mdp.MDPSon;
import br.com.todeschini.persistence.entities.publico.Color;
import br.com.todeschini.persistence.entities.publico.Father;
import br.com.todeschini.persistence.entities.publico.Son;
import br.com.todeschini.persistence.publico.color.ColorRepository;
import br.com.todeschini.persistence.publico.father.FatherDomainToEntityAdapter;
import br.com.todeschini.persistence.publico.father.FatherRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ModulationConfiguratorMethodsImpl implements ModulationConfiguratorMethods {

    private final ColorRepository colorRepository;
    private final MDPItemService mdpItemService;
    private final GhostGeneratorService ghostGeneratorService;
    private final FatherGeneratorService fatherGeneratorService;
    private final SonGeneratorService sonGeneratorService;
    private final FatherDomainToEntityAdapter fatherAdapter;
    private final FatherRepository fatherRepository;

    public ModulationConfiguratorMethodsImpl(ColorRepository colorRepository, MDPItemService mdpItemService, GhostGeneratorService ghostGeneratorService, FatherGeneratorService fatherGeneratorService, SonGeneratorService sonGeneratorService, FatherDomainToEntityAdapter fatherAdapter, FatherRepository fatherRepository) {
        this.colorRepository = colorRepository;
        this.mdpItemService = mdpItemService;
        this.ghostGeneratorService = ghostGeneratorService;
        this.fatherGeneratorService = fatherGeneratorService;
        this.sonGeneratorService = sonGeneratorService;
        this.fatherAdapter = fatherAdapter;
        this.fatherRepository = fatherRepository;
    }

    @Override
    @Transactional
    public List<DFather> generateModulationStruct(DModulationConfigurator configurator) {
        List<Father> fatherList = generateModulationFatherStruct(configurator.getConfig(), configurator.getGhostSuffix(), configurator.getImplementation());

        for(Father father : fatherList){
            Set<String> processedCodes = new HashSet<>();
            for (Son son : father.getSons()) {
                if (son instanceof MDPSon) {
                    String sonCodeSubstring = son.getCode().toString().substring(0, 6);
                    for (ModulationSonConfigurator modulationSonConfigurator : configurator.getConfig().getItems()) {
                        for (SonConfigurator sonConfigurator : modulationSonConfigurator.getSons()) {
                            String sonConfiguratorCode = sonConfigurator.getCode().toString();
                            if (sonConfiguratorCode.equals(sonCodeSubstring) && processedCodes.add(sonConfiguratorCode)) {
                                mdpItemService.generateMDPStruct(new DMDPItem(Collections.singletonList(son),
                                        sonConfigurator.getEdgeLength(), sonConfigurator.getEdgeWidth(), configurator.getGlueCode()));
                                break;
                            }
                        }
                    }
                }
            }

            ghostGeneratorService.generateGhostStruct(new DGhostGenerator(
                    father.getGhost(), configurator.getCornerBracketCode(), configurator.getPlasticCode(), configurator.getNonwovenFabricCode(),
                    configurator.getPolyethyleneCode(), configurator.getUpper(), configurator.getAdditional(), configurator.getWidth(), configurator.getQuantity(), configurator.getOneFace()
            ));

            father.calculateValue();
            father.setImplementation(configurator.getImplementation());
            fatherRepository.save(father);
        }
        return fatherList.stream().map(fatherAdapter::toDomain).collect(Collectors.toList());
    }

    private List<Father> generateModulationFatherStruct(ModulationConfigurator config, String ghostSuffix, LocalDate implementation){
        List<Father> fatherList = new ArrayList<>();
        for(ModulationSonConfigurator item : config.getItems()){
            for (ColorConfigurator colorConfigurator : config.getColors()) {
                Color color = colorRepository.findById(colorConfigurator.getCode()).get();

                Father father = (Father) fatherGeneratorService.createOrUpdateFather( new DFatherGenerator(
                        Long.parseLong(item.getFatherCode().toString() + color.getCode().toString()),
                        item.getFatherDescription(),
                        color));
                ghostGeneratorService.generateGhost(ghostSuffix, father);

                sonGeneratorService.generateOrUpdateSonsAndGuides(
                        (List<Object>) (List<?>) item.getSons(),
                        color,
                        father,
                        null,
                        implementation
                );

                for(Long attachmentId : item.getAttachmentCodes()){
                    fatherGeneratorService.addAttachment(attachmentId, father);
                }

                if (!fatherList.contains(father)) {
                    fatherList.add(father);
                }
            }
        }
        return fatherList;
    }
}
