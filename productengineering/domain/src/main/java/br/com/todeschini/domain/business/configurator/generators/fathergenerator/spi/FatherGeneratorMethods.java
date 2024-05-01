package br.com.todeschini.domain.business.configurator.generators.fathergenerator.spi;

import br.com.todeschini.domain.business.configurator.generators.fathergenerator.DFatherGenerator;
import org.springframework.stereotype.Component;

@Component
public interface FatherGeneratorMethods {

    Object createOrUpdateFather(DFatherGenerator fatherGenerator);

    void addAttachment(Long attachmentId, Object father);
}
