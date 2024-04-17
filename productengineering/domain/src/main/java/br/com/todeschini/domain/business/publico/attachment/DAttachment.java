package br.com.todeschini.domain.business.publico.attachment;

import br.com.todeschini.domain.business.publico.item.DItem;
import br.com.todeschini.domain.validation.NamedValidator;
import br.com.todeschini.domain.validation.ValidationBuilder;
import br.com.todeschini.domain.validation.impl.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DAttachment extends DItem {

    public DAttachment(Long code){
        this.setCode(code);
    }

    @Override
    public void validate(){
        super.validate();
    }
}
