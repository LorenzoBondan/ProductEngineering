package br.com.todeschini.domain.business.publico.attachment.spi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.business.publico.attachment.DAttachment;

import java.util.Collection;

public interface CrudAttachment extends SimpleCrud<DAttachment, Long> {

    Collection<? extends DAttachment> findByDescription (String description);
}
