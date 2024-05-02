package br.com.todeschini.domain.business.configurator.generators.guidegenerator;

import br.com.todeschini.domain.business.configurator.generators.guidegenerator.api.GuideGeneratorService;
import br.com.todeschini.domain.business.configurator.generators.guidegenerator.spi.GuideGeneratorMethods;
import br.com.todeschini.domain.metadata.DomainService;

import java.time.LocalDate;
import java.util.List;

@DomainService
public class GuideGeneratorServiceImpl implements GuideGeneratorService {

    private final GuideGeneratorMethods guideGeneratorMethods;

    public GuideGeneratorServiceImpl(GuideGeneratorMethods guideGeneratorMethods) {
        this.guideGeneratorMethods = guideGeneratorMethods;
    }

    @Override
    public void generateGuideSon(DGuideGenerator guideGenerator) {
        guideGenerator.validate();
        guideGeneratorMethods.generateGuideSon(guideGenerator);
    }
}
