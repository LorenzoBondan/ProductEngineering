package br.com.todeschini.domain.business.configurator.generators.guidegenerator;

import br.com.todeschini.domain.validation.NamedValidator;
import br.com.todeschini.domain.validation.ValidationBuilder;
import br.com.todeschini.domain.validation.impl.DataFuturaValidator;
import br.com.todeschini.domain.validation.impl.ObjetoNaoNuloValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DGuideGenerator {

    private Object son;
    private List<Long> machinesIds;
    private LocalDate implementation;
    private LocalDate finalDate;

    public void validate(){
        new ValidationBuilder()
                .add(new NamedValidator<>("Filho", new ObjetoNaoNuloValidator()), this.son)
                .add(new NamedValidator<>("Lista de Máquinas", new ObjetoNaoNuloValidator()), this.machinesIds)
                .add(new NamedValidator<>("Implementação", new ObjetoNaoNuloValidator()), this.implementation)
                .add(new NamedValidator<>("Implementação", new DataFuturaValidator()), this.implementation)
                .add(new NamedValidator<>("Data Final", new ObjetoNaoNuloValidator()), this.finalDate)
                .add(new NamedValidator<>("Data Final", new DataFuturaValidator()), this.finalDate)
                .validate();
    }
}
