package br.com.todeschini.domain.business.configurator.configurators.mdpconfigurator;

import br.com.todeschini.domain.business.configurator.configurators.mdpconfigurator.api.MDPConfiguratorService;
import br.com.todeschini.domain.business.configurator.configurators.mdpconfigurator.spi.MDPConfiguratorMethods;
import br.com.todeschini.domain.business.publico.father.DFather;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;

@DomainService
public class MDPConfiguratorServiceImpl implements MDPConfiguratorService {

    private final MDPConfiguratorMethods mdpConfiguratorMethods;

    public MDPConfiguratorServiceImpl(MDPConfiguratorMethods mdpConfiguratorMethods) {
        this.mdpConfiguratorMethods = mdpConfiguratorMethods;
    }

    @Override
    public List<DFather> generateMDPStruct(DMDPConfigurator configurator) {
        return mdpConfiguratorMethods.generateMDPStruct(configurator);
    }
}
