package br.com.todeschini.domain.business.configurator.structs.mdfitem.spi;

import br.com.todeschini.domain.business.configurator.structs.mdfitem.DMDFItem;
import org.springframework.stereotype.Service;

@Service
public interface MDFItemMethods {

    void generateMDFStruct(DMDFItem item);
}
