package br.com.todeschini.domain.business.aluminium.glass;

import br.com.todeschini.domain.business.publico.attachment.DAttachment;
import br.com.todeschini.domain.business.publico.color.DColor;
import br.com.todeschini.domain.validation.NamedValidator;
import br.com.todeschini.domain.validation.ValidationBuilder;
import br.com.todeschini.domain.validation.impl.ObjetoNaoNuloValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DGlass extends DAttachment {

    private DColor color;

    public DGlass(Long code){
        this.setCode(code);
    }

    @Override
    public void validate(){
        super.validate();
        new ValidationBuilder()
                .add(new NamedValidator<>("Cor", new ObjetoNaoNuloValidator()), this.color)
                .validate();
    }
}
