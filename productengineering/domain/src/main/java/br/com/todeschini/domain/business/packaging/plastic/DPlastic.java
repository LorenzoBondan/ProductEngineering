package br.com.todeschini.domain.business.packaging.plastic;

import br.com.todeschini.domain.business.basedomains.DBaseMaterial;
import br.com.todeschini.domain.validation.NamedValidator;
import br.com.todeschini.domain.validation.ValidationBuilder;
import br.com.todeschini.domain.validation.impl.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DPlastic extends DBaseMaterial {

    private Double grammage;

    public DPlastic(Long code){
        this.setCode(code);
    }

    @Override
    public void validate(){
        super.validate();
        new ValidationBuilder()
                .add(new NamedValidator<>("Gramatura", new ObjetoNaoNuloValidator()), this.grammage)
                .add(new NamedValidator<>("Gramatura", new NumeroMaiorOuIgualAZeroValidator()), this.grammage)
                .validate();
    }
}
