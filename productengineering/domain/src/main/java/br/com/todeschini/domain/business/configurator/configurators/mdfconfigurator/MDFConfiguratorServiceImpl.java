package br.com.todeschini.domain.business.configurator.configurators.mdfconfigurator;

import br.com.todeschini.domain.business.configurator.configurators.mdfconfigurator.api.MDFConfiguratorService;
import br.com.todeschini.domain.business.configurator.configurators.mdfconfigurator.spi.MDFConfiguratorMethods;
import br.com.todeschini.domain.business.publico.father.DFather;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;

@DomainService
public class MDFConfiguratorServiceImpl implements MDFConfiguratorService {

    private final MDFConfiguratorMethods mdfConfiguratorMethods;

    public MDFConfiguratorServiceImpl(MDFConfiguratorMethods mdfConfiguratorMethods) {
        this.mdfConfiguratorMethods = mdfConfiguratorMethods;
    }

    @Override
    public List<DFather> generateMDFStruct(DMDFConfigurator configurator) {
        return mdfConfiguratorMethods.generateMDFStruct(configurator);
    }
}
