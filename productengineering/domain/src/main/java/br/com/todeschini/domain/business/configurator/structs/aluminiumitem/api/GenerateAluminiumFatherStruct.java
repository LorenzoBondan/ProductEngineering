package br.com.todeschini.domain.business.configurator.structs.aluminiumitem.api;

import br.com.todeschini.domain.configurator.AluminiumConfigurator;

import java.time.LocalDate;
import java.util.List;

public interface GenerateAluminiumFatherStruct {

    List<Object> generateAluminiumFatherStruct(AluminiumConfigurator config, String ghostSuffix, LocalDate implementation);
}
