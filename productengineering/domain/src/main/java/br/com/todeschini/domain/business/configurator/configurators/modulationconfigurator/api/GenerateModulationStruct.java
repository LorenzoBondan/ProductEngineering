package br.com.todeschini.domain.business.configurator.configurators.modulationconfigurator.api;

import br.com.todeschini.domain.business.configurator.configurators.modulationconfigurator.DModulationConfigurator;
import br.com.todeschini.domain.business.publico.father.DFather;

import java.util.List;

public interface GenerateModulationStruct {

    List<DFather> generateModulationStruct(DModulationConfigurator configurator);
}
