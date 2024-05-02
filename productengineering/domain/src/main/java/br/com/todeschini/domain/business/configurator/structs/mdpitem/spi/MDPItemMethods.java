package br.com.todeschini.domain.business.configurator.structs.mdpitem.spi;

import br.com.todeschini.domain.business.configurator.structs.mdpitem.DMDPItem;
import org.springframework.stereotype.Service;

@Service
public interface MDPItemMethods {

    void generateMDPStruct(DMDPItem item);
}
