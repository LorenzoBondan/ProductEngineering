package br.com.todeschini.domain.business.configurator.generators.songenerator;

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
public class DSonGenerator {

    private Long sonCode;
    private String sonDescription;
    private Object color;
    private Object father;
    private Object aluminiumSon;

    public void validate(){
        new ValidationBuilder()
                .add(new NamedValidator<>("Código do Filho", new ObjetoNaoNuloValidator()), this.sonCode)
                .add(new NamedValidator<>("Código do Filho", new NumeroMaiorQueZeroValidator()), this.sonCode)
                .add(new NamedValidator<>("Descrição do Filho", new ObjetoNaoNuloValidator()), this.sonDescription)
                .add(new NamedValidator<>("Descrição do Filho", new NaoBrancoValidator()), this.sonDescription)
                .add(new NamedValidator<>("Descrição do Filho", new CaracteresEspeciaisValidator()), this.sonDescription)
                .add(new NamedValidator<>("Descrição do Filho", new TamanhoMinimoValidator(10)), this.sonDescription)
                .add(new NamedValidator<>("Descrição do Filho", new TamanhoMaximoValidator(100)), this.sonDescription)
                .add(new NamedValidator<>("Cor", new ObjetoNaoNuloValidator()), this.color)
                .add(new NamedValidator<>("Pai", new ObjetoNaoNuloValidator()), this.father)
                .validate();
    }
}
