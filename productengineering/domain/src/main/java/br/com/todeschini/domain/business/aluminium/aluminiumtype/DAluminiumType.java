package br.com.todeschini.domain.business.aluminium.aluminiumtype;

import br.com.todeschini.domain.validation.NamedValidator;
import br.com.todeschini.domain.validation.ValidationBuilder;
import br.com.todeschini.domain.validation.impl.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DAluminiumType {

    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    private Double lessQuantity;

    public DAluminiumType(Long id){
        this.id = id;
    }

    public void validate(){
        new ValidationBuilder()
                .add(new NamedValidator<>("Name", new ObjetoNaoNuloValidator()), this.name)
                .add(new NamedValidator<>("Name", new NaoBrancoValidator()), this.name)
                .add(new NamedValidator<>("Name", new TamanhoMinimoValidator(3)), this.name)
                .add(new NamedValidator<>("Name", new TamanhoMaximoValidator(30)), this.name)
                .add(new NamedValidator<>("Quantidade a menos", new ObjetoNaoNuloValidator()), this.lessQuantity)
                .add(new NamedValidator<>("Quantidade a menos", new NumeroMaiorOuIgualAZeroValidator()), this.lessQuantity)
                .validate();
    }
}
