package br.com.todeschini.persistence.configurator.generators.fathergenerator;

import br.com.todeschini.domain.business.configurator.generators.fathergenerator.DFatherGenerator;
import br.com.todeschini.domain.business.configurator.generators.fathergenerator.spi.FatherGeneratorMethods;
import br.com.todeschini.persistence.entities.publico.Attachment;
import br.com.todeschini.persistence.entities.publico.Color;
import br.com.todeschini.persistence.entities.publico.Father;
import br.com.todeschini.persistence.publico.attachment.AttachmentRepository;
import br.com.todeschini.persistence.publico.father.FatherRepository;
import br.com.todeschini.persistence.publico.item.ItemRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class FatherGeneratorMethodsImpl implements FatherGeneratorMethods {

    private final ItemRepository itemRepository;
    private final FatherRepository fatherRepository;
    private final AttachmentRepository attachmentRepository;

    public FatherGeneratorMethodsImpl(ItemRepository itemRepository, FatherRepository fatherRepository, AttachmentRepository attachmentRepository) {
        this.itemRepository = itemRepository;
        this.fatherRepository = fatherRepository;
        this.attachmentRepository = attachmentRepository;
    }

    @Override
    @Transactional
    public Object createOrUpdateFather(DFatherGenerator fatherGenerator) {
        Father father = itemRepository.existsById(fatherGenerator.getFatherCode()) ? fatherRepository.findById(fatherGenerator.getFatherCode()).get() : new Father();
        if (!itemRepository.existsById(fatherGenerator.getFatherCode())) {
            father.setDescription((fatherGenerator.getFatherDescription() + " - " + ((Color) fatherGenerator.getColor()).getName()).toUpperCase());
            father.setCode(fatherGenerator.getFatherCode());
            father.setColor(((Color) fatherGenerator.getColor()));
            father.setMeasurements();
        }
        return father;
    }

    @Override
    @Transactional
    public void addAttachment(Long attachmentId, Object father) {
        if(attachmentRepository.existsById(attachmentId)){
            Attachment attachment = attachmentRepository.findById(attachmentId).get();
            if (father instanceof Father fatherObject) {
                fatherObject.getAttachments().add(attachment);
                itemRepository.save(fatherObject);
            }
        }
    }
}
