package br.com.todeschini.domain.business.configurator.configurators.modulationconfigurator.spi;

import br.com.todeschini.domain.business.configurator.configurators.modulationconfigurator.DModulationConfigurator;
import br.com.todeschini.domain.business.publico.father.DFather;

import java.util.List;

public interface ModulationConfiguratorMethods {

    List<DFather> generateModulationStruct(DModulationConfigurator configurator);
}
