package br.com.todeschini.domain.business.configurator.generators.ghostgenerator;

import br.com.todeschini.domain.business.configurator.generators.ghostgenerator.api.GhostGeneratorService;
import br.com.todeschini.domain.business.configurator.generators.ghostgenerator.spi.GhostGeneratorMethods;
import br.com.todeschini.domain.metadata.DomainService;
import org.springframework.beans.factory.annotation.Autowired;

@DomainService
public class GhostGeneratorServiceImpl implements GhostGeneratorService {

    @Autowired
    private final GhostGeneratorMethods ghostGeneratorMethods;

    public GhostGeneratorServiceImpl(GhostGeneratorMethods ghostGeneratorMethods) {
        this.ghostGeneratorMethods = ghostGeneratorMethods;
    }

    @Override
    public void generateGhost(String ghostSuffix, Object father) {
        ghostGeneratorMethods.generateGhost(ghostSuffix, father);
    }

    @Override
    public void generateGhostStruct(DGhostGenerator ghostGenerator) {
        ghostGenerator.validate();
        ghostGeneratorMethods.generateGhostStruct(ghostGenerator);
    }
}
