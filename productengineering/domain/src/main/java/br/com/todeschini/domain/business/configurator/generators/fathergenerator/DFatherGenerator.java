package br.com.todeschini.domain.business.configurator.generators.fathergenerator;

import br.com.todeschini.domain.validation.NamedValidator;
import br.com.todeschini.domain.validation.ValidationBuilder;
import br.com.todeschini.domain.validation.impl.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DFatherGenerator {

    private Long fatherCode;
    private String fatherDescription;
    private Object color;

    public void validate(){
        new ValidationBuilder()
                .add(new NamedValidator<>("Código pai", new ObjetoNaoNuloValidator()), this.fatherCode)
                .add(new NamedValidator<>("Código pai", new NumeroMaiorQueZeroValidator()), this.fatherCode)
                .add(new NamedValidator<>("Descrição", new ObjetoNaoNuloValidator()), this.fatherDescription)
                .add(new NamedValidator<>("Descrição", new NaoBrancoValidator()), this.fatherDescription)
                .add(new NamedValidator<>("Descrição", new TamanhoMinimoValidator(10)), this.fatherDescription)
                .add(new NamedValidator<>("Descrição", new TamanhoMaximoValidator(100)), this.fatherDescription)
                .add(new NamedValidator<>("Cor", new ObjetoNaoNuloValidator()), this.color)
                .validate();
    }
}
