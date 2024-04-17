package br.com.todeschini.domain.business.guides.guidemachine;

import br.com.todeschini.domain.exceptions.ValidationException;
import br.com.todeschini.domain.validation.NamedValidator;
import br.com.todeschini.domain.validation.ValidationBuilder;
import br.com.todeschini.domain.validation.impl.DataFuturaValidator;
import br.com.todeschini.domain.validation.impl.NumeroMaiorOuIgualAZeroValidator;
import br.com.todeschini.domain.validation.impl.ObjetoNaoNuloValidator;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DGuideMachine {

    @EqualsAndHashCode.Include
    private Long id;
    private Long guideId;
    private Long machineId;
    private Double machineTime;
    private Double manTime;
    private String measurementUnit;

    //private List<DGuideMachine> guideMachines = new ArrayList<>();

    public DGuideMachine(Long id){
        this.id = id;
    }

    public void validate() throws ValidationException {
        new ValidationBuilder()
                .add(new NamedValidator<>("Máquina", new ObjetoNaoNuloValidator()), this.machineId)
                .add(new NamedValidator<>("Guia", new ObjetoNaoNuloValidator()), this.guideId)
                .add(new NamedValidator<>("Tempo de Máquina", new NumeroMaiorOuIgualAZeroValidator()), this.machineTime)
                .add(new NamedValidator<>("Tempo Homem", new NumeroMaiorOuIgualAZeroValidator()), this.manTime)
                .validate();
    }
}
