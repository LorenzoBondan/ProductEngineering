package br.com.todeschini.domain.business.configurator.structs.aluminiumitem.spi;

import br.com.todeschini.domain.business.configurator.structs.aluminiumitem.DAluminiumItem;
import br.com.todeschini.domain.configurator.AluminiumConfigurator;

import java.time.LocalDate;
import java.util.List;

public interface AluminiumItemMethods {

    List<Object> generateAluminiumFatherStruct(AluminiumConfigurator config, String ghostSuffix, LocalDate implementation);
    void generateAluminiumStruct(DAluminiumItem aluminiumItem);
}
