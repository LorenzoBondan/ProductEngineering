package br.com.todeschini.domain.business.configurator.configurators.mdpconfigurator.spi;

import br.com.todeschini.domain.business.configurator.configurators.mdpconfigurator.DMDPConfigurator;
import br.com.todeschini.domain.business.publico.father.DFather;

import java.util.List;

public interface MDPConfiguratorMethods {

    List<DFather> generateMDPStruct(DMDPConfigurator configurator);
}
