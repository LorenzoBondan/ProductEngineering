package br.com.todeschini.domain.business.configurator.generators.ghostgenerator.spi;

import br.com.todeschini.domain.business.configurator.generators.ghostgenerator.DGhostGenerator;
import org.springframework.stereotype.Service;

@Service
public interface GhostGeneratorMethods {

    void generateGhost(String ghostSuffix, Object father);

    void generateGhostStruct(DGhostGenerator ghostGenerator);
}
