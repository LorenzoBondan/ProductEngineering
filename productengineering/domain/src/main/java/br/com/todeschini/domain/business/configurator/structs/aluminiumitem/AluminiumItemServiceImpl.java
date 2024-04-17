package br.com.todeschini.domain.business.configurator.structs.aluminiumitem;

import br.com.todeschini.domain.business.configurator.structs.aluminiumitem.api.AluminiumItemService;
import br.com.todeschini.domain.business.configurator.structs.aluminiumitem.spi.AluminiumItemMethods;
import br.com.todeschini.domain.configurator.AluminiumConfigurator;
import br.com.todeschini.domain.metadata.DomainService;

import java.time.LocalDate;
import java.util.List;

@DomainService
public class AluminiumItemServiceImpl implements AluminiumItemService {

    private final AluminiumItemMethods aluminiumItemMethods;

    public AluminiumItemServiceImpl(AluminiumItemMethods aluminiumItemMethods) {
        this.aluminiumItemMethods = aluminiumItemMethods;
    }

    @Override
    public void generateAluminiumStruct(DAluminiumItem item) {
        item.validate();
        aluminiumItemMethods.generateAluminiumStruct(item);
    }

    @Override
    public List<Object> generateAluminiumFatherStruct(AluminiumConfigurator config, String ghostSuffix, LocalDate implementation) {
        return aluminiumItemMethods.generateAluminiumFatherStruct(config, ghostSuffix, implementation);
    }
}
