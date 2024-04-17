package br.com.todeschini.persistence.configurator.configurators.aluminiumconfigurator;

import br.com.todeschini.domain.business.configurator.configurators.aluminiumconfigurator.DAluminiumConfigurator;
import br.com.todeschini.domain.business.configurator.configurators.aluminiumconfigurator.spi.AluminiumConfiguratorMethods;
import br.com.todeschini.domain.business.configurator.generators.ghostgenerator.DGhostGenerator;
import br.com.todeschini.domain.business.configurator.generators.ghostgenerator.api.GhostGeneratorService;
import br.com.todeschini.domain.business.configurator.structs.aluminiumitem.DAluminiumItem;
import br.com.todeschini.domain.business.configurator.structs.aluminiumitem.api.AluminiumItemService;
import br.com.todeschini.domain.business.publico.father.DFather;
import br.com.todeschini.persistence.entities.aluminium.AluminiumSon;
import br.com.todeschini.persistence.entities.publico.Father;
import br.com.todeschini.persistence.publico.father.FatherDomainToEntityAdapter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AluminiumConfiguratorMethodsImpl implements AluminiumConfiguratorMethods {

    private final AluminiumItemService aluminiumItemService;
    private final GhostGeneratorService ghostGeneratorService;
    private final FatherDomainToEntityAdapter fatherAdapter;

    public AluminiumConfiguratorMethodsImpl(AluminiumItemService aluminiumItemService, GhostGeneratorService ghostGeneratorService, FatherDomainToEntityAdapter fatherAdapter) {
        this.aluminiumItemService = aluminiumItemService;
        this.ghostGeneratorService = ghostGeneratorService;
        this.fatherAdapter = fatherAdapter;
    }

    @Override
    @Transactional
    public List<DFather> generateStruct(DAluminiumConfigurator configurator) {
        List<Object> fatherList = aluminiumItemService.generateAluminiumFatherStruct(configurator.getConfig(), configurator.getGhostSuffix(), configurator.getImplementation());

        for(Object obj : fatherList){
            if(obj instanceof Father father){
                List<AluminiumSon> aluminiumSons =  father.getSons().stream()
                        .filter(son -> son instanceof AluminiumSon)
                        .map(son -> (AluminiumSon) son)
                        .collect(Collectors.toList());

                aluminiumItemService.generateAluminiumStruct(new DAluminiumItem((List<Object>) (List<?>) aluminiumSons, configurator.getConfig(), configurator.getAluminiumTypeId(),
                        configurator.getDrawerPullCode(), configurator.getTrySquareCode(), configurator.getTrySquareQuantity(), configurator.getConfig().getScrews(), configurator.getMoldingCode(),
                        configurator.getGlueCode()));

                ghostGeneratorService.generateGhostStruct(new DGhostGenerator(father.getGhost(),
                        configurator.getCornerBracketCode(), configurator.getPlasticCode(), configurator.getNonwovenFabricCode(), configurator.getPolyethyleneCode(),
                        configurator.getUpper(), configurator.getAdditional(), configurator.getWidth(),
                        configurator.getQuantity(),
                        configurator.getOneFace())
                );
            }
        }

        List<DFather> dFatherList = new ArrayList<>();
        for(Object obj : fatherList){
            if(obj instanceof Father father){
                dFatherList.add(fatherAdapter.toDomain(father));
            }
        }
        return dFatherList;
    }
}
