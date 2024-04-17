package br.com.todeschini.domain.business.publico.attachment;

import br.com.todeschini.domain.business.publico.attachment.api.AttachmentService;
import br.com.todeschini.domain.business.publico.attachment.spi.CrudAttachment;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;
import java.util.Optional;

@DomainService
public class AttachmentServiceImpl implements AttachmentService {

    private final CrudAttachment crudAttachment;

    public AttachmentServiceImpl(CrudAttachment crudAttachment) {
        this.crudAttachment = crudAttachment;
    }

    @Override
    public List<DAttachment> findAllActiveAndCurrentOne(Long id) {
        return crudAttachment.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DAttachment find(Long id) {
        return crudAttachment.find(id);
    }

    @Override
    public DAttachment insert(DAttachment domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudAttachment.insert(domain);
    }

    @Override
    public DAttachment update(Long id, DAttachment domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudAttachment.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudAttachment.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudAttachment.delete(id);
    }

    private void validateDuplicatedResource(DAttachment domain){
        if(crudAttachment.findByDescription(domain.getDescription())
                .stream()
                .anyMatch(t -> !t.getCode().equals(Optional.ofNullable(domain.getCode()).orElse(-1L)))){
            throw new DuplicatedResourceException("Verifique o campo descrição.");
        }
    }
}
