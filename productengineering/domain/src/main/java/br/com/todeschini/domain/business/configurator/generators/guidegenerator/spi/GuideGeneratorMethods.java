package br.com.todeschini.domain.business.configurator.generators.guidegenerator.spi;

import br.com.todeschini.domain.business.configurator.generators.guidegenerator.DGuideGenerator;
import org.springframework.stereotype.Service;

@Service
public interface GuideGeneratorMethods {

    void generateGuideSon(DGuideGenerator guideGenerator);
}
