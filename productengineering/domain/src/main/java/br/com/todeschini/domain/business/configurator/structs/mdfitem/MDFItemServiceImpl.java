package br.com.todeschini.domain.business.configurator.structs.mdfitem;

import br.com.todeschini.domain.business.configurator.structs.mdfitem.api.MDFItemService;
import br.com.todeschini.domain.business.configurator.structs.mdfitem.spi.MDFItemMethods;
import br.com.todeschini.domain.metadata.DomainService;

@DomainService
public class MDFItemServiceImpl implements MDFItemService {

    private final MDFItemMethods mdfItemMethods;

    public MDFItemServiceImpl(MDFItemMethods mdfItemMethods) {
        this.mdfItemMethods = mdfItemMethods;
    }

    @Override
    public void generateMDFStruct(DMDFItem item) {
        item.validate();
        mdfItemMethods.generateMDFStruct(item);
    }
}
