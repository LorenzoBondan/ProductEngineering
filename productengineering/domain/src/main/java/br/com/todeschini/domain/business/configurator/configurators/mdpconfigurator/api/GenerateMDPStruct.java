package br.com.todeschini.domain.business.configurator.configurators.mdpconfigurator.api;

import br.com.todeschini.domain.business.configurator.configurators.mdpconfigurator.DMDPConfigurator;
import br.com.todeschini.domain.business.publico.father.DFather;

import java.util.List;

public interface GenerateMDPStruct {

    List<DFather> generateMDPStruct(DMDPConfigurator configurator);
}
