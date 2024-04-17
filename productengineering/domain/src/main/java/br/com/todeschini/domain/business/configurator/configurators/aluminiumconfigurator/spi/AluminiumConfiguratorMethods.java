package br.com.todeschini.domain.business.configurator.configurators.aluminiumconfigurator.spi;

import br.com.todeschini.domain.business.configurator.configurators.aluminiumconfigurator.DAluminiumConfigurator;
import br.com.todeschini.domain.business.publico.father.DFather;

import java.util.List;

public interface AluminiumConfiguratorMethods {

    List<DFather> generateStruct(DAluminiumConfigurator configurator);
}
