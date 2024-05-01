package br.com.todeschini.domain.business.configurator.generators.songenerator;

import br.com.todeschini.domain.business.configurator.generators.songenerator.api.SonGeneratorService;
import br.com.todeschini.domain.business.configurator.generators.songenerator.spi.SonGeneratorMethods;
import br.com.todeschini.domain.metadata.DomainService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

@DomainService
public class SonGeneratorServiceImpl implements SonGeneratorService {

    @Autowired
    private final SonGeneratorMethods sonGeneratorMethods;

    public SonGeneratorServiceImpl(SonGeneratorMethods sonGeneratorMethods) {
        this.sonGeneratorMethods = sonGeneratorMethods;
    }

    @Override
    public Object createOrUpdateAluminiumSon(DSonGenerator sonGenerator) {
        sonGenerator.validate();
        return sonGeneratorMethods.createOrUpdateAluminiumSon(sonGenerator);
    }

    @Override
    public Object createOrUpdateMDPSon(DSonGenerator sonGenerator) {
        sonGenerator.validate();
        return sonGeneratorMethods.createOrUpdateMDPSon(sonGenerator);
    }

    @Override
    public Object createOrUpdatePaintingSon(DSonGenerator sonGenerator) {
        sonGenerator.validate();
        return sonGeneratorMethods.createOrUpdatePaintingSon(sonGenerator);
    }

    @Override
    public void generateOrUpdateSonsAndGuides(List<Object> sonConfiguratorList, Object color, Object father, Object aluminiumSon, LocalDate implementation) {
        sonGeneratorMethods.generateOrUpdateSonsAndGuides(sonConfiguratorList, color, father, aluminiumSon, implementation);
    }
}
