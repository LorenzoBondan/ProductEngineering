package br.com.todeschini.domain.business.configurator.configurators.modulationconfigurator;

import br.com.todeschini.domain.business.configurator.configurators.modulationconfigurator.api.ModulationConfiguratorService;
import br.com.todeschini.domain.business.configurator.configurators.modulationconfigurator.spi.ModulationConfiguratorMethods;
import br.com.todeschini.domain.business.publico.father.DFather;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;

@DomainService
public class ModulationConfiguratorServiceImpl implements ModulationConfiguratorService {

    private final ModulationConfiguratorMethods modulationConfiguratorMethods;

    public ModulationConfiguratorServiceImpl(ModulationConfiguratorMethods modulationConfiguratorMethods) {
        this.modulationConfiguratorMethods = modulationConfiguratorMethods;
    }

    @Override
    public List<DFather> generateModulationStruct(DModulationConfigurator configurator) {
        return modulationConfiguratorMethods.generateModulationStruct(configurator);
    }
}
