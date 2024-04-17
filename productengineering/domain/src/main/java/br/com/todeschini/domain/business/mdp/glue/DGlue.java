package br.com.todeschini.domain.business.mdp.glue;

import br.com.todeschini.domain.business.basedomains.DBaseMaterial;
import br.com.todeschini.domain.validation.NamedValidator;
import br.com.todeschini.domain.validation.ValidationBuilder;
import br.com.todeschini.domain.validation.impl.NumeroMaiorQueZeroValidator;
import br.com.todeschini.domain.validation.impl.ObjetoNaoNuloValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DGlue extends DBaseMaterial {

    private Double grammage;

    public DGlue(Long code){
        this.setCode(code);
    }

    @Override
    public void validate(){
        super.validate();
        new ValidationBuilder()
                .add(new NamedValidator<>("Gramatura", new ObjetoNaoNuloValidator()), this.grammage)
                .add(new NamedValidator<>("Gramatura", new NumeroMaiorQueZeroValidator()), this.grammage)
                .validate();
    }
}
