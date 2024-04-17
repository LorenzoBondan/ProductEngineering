package br.com.todeschini.domain.business.configurator.structs.mdpitem;

import br.com.todeschini.domain.business.configurator.structs.mdpitem.api.MDPItemService;
import br.com.todeschini.domain.business.configurator.structs.mdpitem.spi.MDPItemMethods;
import br.com.todeschini.domain.metadata.DomainService;

@DomainService
public class MDPItemServiceImpl implements MDPItemService {

    private final MDPItemMethods mdpItemMethods;

    public MDPItemServiceImpl(MDPItemMethods mdpItemMethods) {
        this.mdpItemMethods = mdpItemMethods;
    }

    @Override
    public void generateMDPStruct(DMDPItem item) {
        item.validate();
        mdpItemMethods.generateMDPStruct(item);
    }
}
