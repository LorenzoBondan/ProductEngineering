package br.com.todeschini.domain.business.configurator.generators.songenerator.spi;

import br.com.todeschini.domain.business.configurator.generators.songenerator.DSonGenerator;

import java.time.LocalDate;
import java.util.List;

public interface SonGeneratorMethods {

    Object createOrUpdateMDPSon(DSonGenerator sonGenerator);
    Object createOrUpdatePaintingSon(DSonGenerator sonGenerator);
    Object createOrUpdateAluminiumSon(DSonGenerator sonGenerator);
    void generateOrUpdateSonsAndGuides(List<Object> sonConfiguratorList, Object color, Object father, Object aluminiumSon, LocalDate implementation);
}
