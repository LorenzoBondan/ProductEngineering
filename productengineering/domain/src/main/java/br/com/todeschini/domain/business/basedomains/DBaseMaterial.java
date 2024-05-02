package br.com.todeschini.domain.business.basedomains;

import br.com.todeschini.domain.business.publico.color.DColor;
import br.com.todeschini.domain.exceptions.ValidationException;
import br.com.todeschini.domain.validation.NamedValidator;
import br.com.todeschini.domain.validation.ValidationBuilder;
import br.com.todeschini.domain.validation.impl.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DBaseMaterial {

    @EqualsAndHashCode.Include
    private Long code;
    private String description;
    private String family;
    private LocalDate implementation;
    private Double lostPercentage;
    private DColor color;
    private Double value;

    public void validate() throws ValidationException {
        new ValidationBuilder()
                .add(new NamedValidator<>("Código", new ObjetoNaoNuloValidator()), this.code)
                .add(new NamedValidator<>("Código", new NumeroMaiorQueZeroValidator()), this.code)
                .add(new NamedValidator<>("Código", new LongTamanhoMinimoValidator(7)), this.code)
                .add(new NamedValidator<>("Código", new LongTamanhoMaximoValidator(10)), this.code)
                .add(new NamedValidator<>("Descrição", new ObjetoNaoNuloValidator()), this.description)
                .add(new NamedValidator<>("Descrição", new NaoBrancoValidator()), this.description)
                .add(new NamedValidator<>("Descrição", new TamanhoMinimoValidator(3)), this.description)
                .add(new NamedValidator<>("Descrição", new TamanhoMaximoValidator(100)), this.description)
                .add(new NamedValidator<>("Família", new NaoBrancoValidator()), this.family)
                .add(new NamedValidator<>("Família", new TamanhoMinimoValidator(3)), this.family)
                .add(new NamedValidator<>("Família", new TamanhoMaximoValidator(10)), this.family)
                .add(new NamedValidator<>("Implementação", new DataFuturaValidator()), this.implementation)
                .add(new NamedValidator<>("Porcentagem de perda", new NumeroMaiorOuIgualAZeroValidator()), this.lostPercentage)
                .add(new NamedValidator<>("Valor", new NumeroMaiorQueZeroValidator()), this.value)
                .validate();
    }
}
