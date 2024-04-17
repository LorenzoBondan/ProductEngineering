package br.com.todeschini.domain.business.configurator.configurators.aluminiumconfigurator;

import br.com.todeschini.domain.business.configurator.configurators.aluminiumconfigurator.api.AluminiumConfiguratorService;
import br.com.todeschini.domain.business.configurator.configurators.aluminiumconfigurator.spi.AluminiumConfiguratorMethods;
import br.com.todeschini.domain.business.publico.father.DFather;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;

@DomainService
public class AluminiumConfiguratorServiceImpl implements AluminiumConfiguratorService {

    private final AluminiumConfiguratorMethods aluminiumConfiguratorMethods;

    public AluminiumConfiguratorServiceImpl(AluminiumConfiguratorMethods aluminiumConfiguratorMethods) {
        this.aluminiumConfiguratorMethods = aluminiumConfiguratorMethods;
    }

    @Override
    public List<DFather> generateStruct(DAluminiumConfigurator configurator) {
        return aluminiumConfiguratorMethods.generateStruct(configurator);
    }
}
