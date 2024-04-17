package br.com.todeschini.domain.business.configurator.generators.songenerator.api;

import java.time.LocalDate;
import java.util.List;

public interface GenerateOrUpdateSonsAndGuides {

    void generateOrUpdateSonsAndGuides(List<Object> sonConfiguratorList, Object color, Object father, Object aluminiumSon, LocalDate implementation);
}
