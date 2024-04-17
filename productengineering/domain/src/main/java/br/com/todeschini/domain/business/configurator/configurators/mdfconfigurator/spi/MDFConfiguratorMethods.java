package br.com.todeschini.domain.business.configurator.configurators.mdfconfigurator.spi;

import br.com.todeschini.domain.business.configurator.configurators.mdfconfigurator.DMDFConfigurator;
import br.com.todeschini.domain.business.publico.father.DFather;

import java.util.List;

public interface MDFConfiguratorMethods {

    List<DFather> generateMDFStruct(DMDFConfigurator configurator);
}
