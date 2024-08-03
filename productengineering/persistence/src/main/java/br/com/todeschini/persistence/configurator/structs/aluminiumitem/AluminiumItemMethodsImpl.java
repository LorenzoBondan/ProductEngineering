package br.com.todeschini.persistence.configurator.structs.aluminiumitem;

import br.com.todeschini.domain.business.aluminium.useddrawerpull.api.UsedDrawerPullService;
import br.com.todeschini.domain.business.aluminium.usedglass.api.UsedGlassService;
import br.com.todeschini.domain.business.aluminium.usedmolding.api.UsedMoldingService;
import br.com.todeschini.domain.business.aluminium.usedscrew.api.UsedScrewService;
import br.com.todeschini.domain.business.aluminium.usedtrysquare.api.UsedTrySquareService;
import br.com.todeschini.domain.business.configurator.generators.fathergenerator.DFatherGenerator;
import br.com.todeschini.domain.business.configurator.generators.fathergenerator.api.FatherGeneratorService;
import br.com.todeschini.domain.business.configurator.generators.ghostgenerator.api.GhostGeneratorService;
import br.com.todeschini.domain.business.configurator.generators.songenerator.DSonGenerator;
import br.com.todeschini.domain.business.configurator.generators.songenerator.api.SonGeneratorService;
import br.com.todeschini.domain.business.configurator.structs.aluminiumitem.DAluminiumItem;
import br.com.todeschini.domain.business.configurator.structs.aluminiumitem.spi.AluminiumItemMethods;
import br.com.todeschini.domain.business.configurator.structs.mdpitem.DMDPItem;
import br.com.todeschini.domain.business.configurator.structs.mdpitem.api.MDPItemService;
import br.com.todeschini.domain.configurator.*;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.aluminium.aluminiumson.AluminiumSonRepository;
import br.com.todeschini.persistence.aluminium.aluminiumtype.AluminiumTypeRepository;
import br.com.todeschini.persistence.aluminium.drawerpull.DrawerPullRepository;
import br.com.todeschini.persistence.aluminium.glass.GlassRepository;
import br.com.todeschini.persistence.aluminium.molding.MoldingRepository;
import br.com.todeschini.persistence.aluminium.screw.ScrewRepository;
import br.com.todeschini.persistence.aluminium.trysquare.TrySquareRepository;
import br.com.todeschini.persistence.aluminium.useddrawerpull.UsedDrawerPullDomainToEntityAdapter;
import br.com.todeschini.persistence.aluminium.usedglass.UsedGlassDomainToEntityAdapter;
import br.com.todeschini.persistence.aluminium.usedmolding.UsedMoldingDomainToEntityAdapter;
import br.com.todeschini.persistence.aluminium.usedscrew.UsedScrewDomainToEntityAdapter;
import br.com.todeschini.persistence.aluminium.usedtrysquare.UsedTrySquareDomainToEntityAdapter;
import br.com.todeschini.persistence.entities.aluminium.*;
import br.com.todeschini.persistence.entities.publico.Color;
import br.com.todeschini.persistence.entities.publico.Father;
import br.com.todeschini.persistence.entities.publico.Son;
import br.com.todeschini.persistence.publico.color.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Component
public class AluminiumItemMethodsImpl implements AluminiumItemMethods {

    private final ColorRepository colorRepository;
    private final GlassRepository glassRepository;
    private final AluminiumTypeRepository aluminiumTypeRepository;
    private final MDPItemService mdpItemService;
    private final GhostGeneratorService ghostGeneratorService;
    private final FatherGeneratorService fatherGeneratorService;
    private final SonGeneratorService sonGeneratorService;
    private final DrawerPullRepository drawerPullRepository;
    private final TrySquareRepository trySquareRepository;
    private final ScrewRepository screwRepository;
    private final AluminiumSonRepository aluminiumSonRepository;
    private final MoldingRepository moldingRepository;

    @Autowired
    private UsedGlassService usedGlassService;
    @Autowired
    private UsedDrawerPullService usedDrawerPullService;
    @Autowired
    private UsedMoldingService usedMoldingService;
    @Autowired
    private UsedScrewService usedScrewService;
    @Autowired
    private UsedTrySquareService usedTrySquareService;
    @Autowired
    private UsedDrawerPullDomainToEntityAdapter usedDrawerPullDomainToEntityAdapter;
    @Autowired
    private UsedGlassDomainToEntityAdapter usedGlassDomainToEntityAdapter;
    @Autowired
    private UsedMoldingDomainToEntityAdapter usedMoldingDomainToEntityAdapter;
    @Autowired
    private UsedTrySquareDomainToEntityAdapter usedTrySquareDomainToEntityAdapter;
    @Autowired
    private UsedScrewDomainToEntityAdapter usedScrewDomainToEntityAdapter;

    public AluminiumItemMethodsImpl(ColorRepository colorRepository, GlassRepository glassRepository, AluminiumTypeRepository aluminiumTypeRepository, MDPItemService mdpItemService, GhostGeneratorService ghostGeneratorService, FatherGeneratorService fatherGeneratorService, SonGeneratorService sonGeneratorService, DrawerPullRepository drawerPullRepository, TrySquareRepository trySquareRepository, ScrewRepository screwRepository, AluminiumSonRepository aluminiumSonRepository, MoldingRepository moldingRepository) {
        this.colorRepository = colorRepository;
        this.glassRepository = glassRepository;
        this.aluminiumTypeRepository = aluminiumTypeRepository;
        this.mdpItemService = mdpItemService;
        this.ghostGeneratorService = ghostGeneratorService;
        this.fatherGeneratorService = fatherGeneratorService;
        this.sonGeneratorService = sonGeneratorService;
        this.drawerPullRepository = drawerPullRepository;
        this.trySquareRepository = trySquareRepository;
        this.screwRepository = screwRepository;
        this.aluminiumSonRepository = aluminiumSonRepository;
        this.moldingRepository = moldingRepository;
    }

    @Override
    @Transactional
    public List<Object> generateAluminiumFatherStruct(AluminiumConfigurator config, String ghostSuffix, LocalDate implementation) {
        List<Father> fatherList = new ArrayList<>();
        for(AluminiumSonConfigurator aluminiumSonConfigurator : config.getItems()){
            for(ColorConfigurator colorConfigurator : config.getColors()){
                Color color = colorRepository.findById(colorConfigurator.getCode()).get();

                Father father = (Father) fatherGeneratorService.createOrUpdateFather(new DFatherGenerator(
                        Long.parseLong(aluminiumSonConfigurator.getFatherCode().toString() + color.getCode().toString()),
                        (aluminiumSonConfigurator.getDescription()),
                        color));
                ghostGeneratorService.generateGhost(ghostSuffix, father);

                AluminiumSon aluminiumSon = (AluminiumSon) sonGeneratorService.createOrUpdateAluminiumSon(new DSonGenerator(
                        aluminiumSonConfigurator.getAluminiumSonCode(),
                        aluminiumSonConfigurator.getDescription(),
                        color,
                        father,
                        null));

                sonGeneratorService.generateOrUpdateSonsAndGuides((List<Object>) (List<?>) aluminiumSonConfigurator.getSons(),
                        color,
                        null,
                        aluminiumSon,
                        implementation);

                if (!fatherList.contains(father)) {
                    fatherList.add(father);
                }
            }
        }

        return new ArrayList<>(fatherList);
    }

    @Override
    @Transactional
    public void generateAluminiumStruct(DAluminiumItem aluminiumItem) {
        for (Object obj : aluminiumItem.getList()) {
            if(obj instanceof AluminiumSon son){
                setAluminiumType(son, aluminiumItem.getAluminiumTypeId());
                processMDPSons(son, aluminiumItem.getConfig(), aluminiumItem.getGlueCode());
                setDrawerPull(son, aluminiumItem.getDrawerPullCode());
                setGlass(son);
                addUsedMolding(son, aluminiumItem.getMoldingCode());
                addUsedTrySquare(son, aluminiumItem.getTrySquareCode(), aluminiumItem.getTrySquareQuantity());
                addUsedScrews(son, aluminiumItem.getScrews());
                son.calculateValue();
                aluminiumSonRepository.save(son);
            }
        }
    }

    private void setAluminiumType(AluminiumSon son, Long aluminiumTypeId) {
        AluminiumType aluminiumType = aluminiumTypeRepository.findById(aluminiumTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de Alumínio não encontrado"));
        son.setAluminiumType(aluminiumType);
    }

    private void processMDPSons(AluminiumSon son, AluminiumConfigurator config, Long glueCode) {
        Set<String> processedCodes = new HashSet<>();
        for (Son aluminiumSonSon : son.getSons()) {
            String sonCodeSubstring = aluminiumSonSon.getCode().toString().substring(0, 6);
            for (AluminiumSonConfigurator aluminiumSonConfigurator : config.getItems()) {
                for (SonConfigurator sonConfigurator : aluminiumSonConfigurator.getSons()) {
                    String sonConfiguratorCode = sonConfigurator.getCode().toString();
                    if (sonConfiguratorCode.equals(sonCodeSubstring) && processedCodes.add(sonConfiguratorCode)) {
                        mdpItemService.generateMDPStruct(new DMDPItem(Collections.singletonList(aluminiumSonSon),
                                sonConfigurator.getEdgeLength(), sonConfigurator.getEdgeWidth(), glueCode));
                        break;
                    }
                }
            }
        }
    }

    private void setDrawerPull(AluminiumSon son, Long drawerPullCode) {
        DrawerPull drawerPull = drawerPullRepository.findById(drawerPullCode)
                .orElseThrow(() -> new ResourceNotFoundException("Puxador não encontrado"));
        UsedDrawerPull usedDrawerPull = new UsedDrawerPull();
        usedDrawerPull.setDrawerPull(drawerPull);
        usedDrawerPull.setAluminiumSon(son);
        usedDrawerPull.calculateQuantity();
        usedDrawerPullService.insert(usedDrawerPullDomainToEntityAdapter.toDomain(usedDrawerPull));
    }

    private void setGlass(AluminiumSon son) {
        Glass glass = glassRepository.findGlassByColorIdAndMeasures(son.getColor().getCode(), son.getMeasure1(), son.getMeasure2());
        UsedGlass usedGlass = new UsedGlass();
        usedGlass.setGlass(glass);
        usedGlass.setAluminiumSon(son);
        usedGlass.calculateQuantity();
        usedGlassService.insert(usedGlassDomainToEntityAdapter.toDomain(usedGlass));
    }

    private void addUsedMolding(AluminiumSon son, Long moldingCode) {
        UsedMolding usedMolding = new UsedMolding();
        usedMolding.setAluminiumSon(son);
        usedMolding.setMolding(moldingRepository.findById(moldingCode)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado")));
        usedMolding.calculateQuantity();
        usedMoldingService.insert(usedMoldingDomainToEntityAdapter.toDomain(usedMolding));
    }

    private void addUsedTrySquare(AluminiumSon son, Long trySquareCode, Integer trySquareQuantity) {
        UsedTrySquare usedTrySquare = new UsedTrySquare();
        usedTrySquare.setAluminiumSon(son);
        usedTrySquare.setTrySquare(trySquareRepository.findById(trySquareCode)
                .orElseThrow(() -> new ResourceNotFoundException("Esquadreta não encontrada")));
        usedTrySquare.setQuantity(Double.valueOf(trySquareQuantity));
        usedTrySquare.calculateQuantity();
        usedTrySquareService.insert(usedTrySquareDomainToEntityAdapter.toDomain(usedTrySquare));
    }

    private void addUsedScrews(AluminiumSon son, List<ScrewConfigurator> screws) {
        for (ScrewConfigurator screw : screws) {
            UsedScrew usedScrew = new UsedScrew();
            usedScrew.setAluminiumSon(son);
            usedScrew.setScrew(screwRepository.findById(screw.getCode())
                    .orElseThrow(() -> new ResourceNotFoundException("Parafuso não encontrado")));
            usedScrew.setQuantity(Double.valueOf(screw.getQuantity()));
            usedScrew.calculateQuantity();
            usedScrewService.insert(usedScrewDomainToEntityAdapter.toDomain(usedScrew));
        }
    }
}
