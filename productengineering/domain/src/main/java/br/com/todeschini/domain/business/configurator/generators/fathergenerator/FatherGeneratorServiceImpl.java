package br.com.todeschini.domain.business.configurator.generators.fathergenerator;

import br.com.todeschini.domain.business.configurator.generators.fathergenerator.api.FatherGeneratorService;
import br.com.todeschini.domain.business.configurator.generators.fathergenerator.spi.FatherGeneratorMethods;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;

@DomainService
public class FatherGeneratorServiceImpl implements FatherGeneratorService {

    private final FatherGeneratorMethods fatherGeneratorMethods;

    public FatherGeneratorServiceImpl(FatherGeneratorMethods fatherGeneratorMethods) {
        this.fatherGeneratorMethods = fatherGeneratorMethods;
    }

    @Override
    public void addAttachment(Long attachmentId, Object father) {
        fatherGeneratorMethods.addAttachment(attachmentId, father);
    }

    @Override
    public Object createOrUpdateFather(DFatherGenerator fatherGenerator) {
        fatherGenerator.validate();
        return fatherGeneratorMethods.createOrUpdateFather(fatherGenerator);
    }
}
