package br.com.todeschini.domain.business.configurator.configurators.mdfconfigurator.api;

import br.com.todeschini.domain.business.configurator.configurators.mdfconfigurator.DMDFConfigurator;
import br.com.todeschini.domain.business.publico.father.DFather;

import java.util.List;

public interface GenerateMDFStruct {

    List<DFather> generateMDFStruct(DMDFConfigurator configurator);
}
