package br.com.todeschini.domain.business.guides.guide;

import br.com.todeschini.domain.business.enums.DStatus;
import br.com.todeschini.domain.business.guides.guidemachine.DGuideMachine;
import br.com.todeschini.domain.exceptions.ValidationException;
import br.com.todeschini.domain.validation.NamedValidator;
import br.com.todeschini.domain.validation.ValidationBuilder;
import br.com.todeschini.domain.validation.impl.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DGuide {

    @EqualsAndHashCode.Include
    private Long id;
    private String description;
    private LocalDate implementation;
    private LocalDate finalDate;
    private Double value;
    private DStatus status;

    private List<DGuideMachine> guideMachines = new ArrayList<>();

    public DGuide(Long id){
        this.id = id;
    }

    public void validate() throws ValidationException {
        new ValidationBuilder()
                .add(new NamedValidator<>("Descrição", new ObjetoNaoNuloValidator()), this.description)
                .add(new NamedValidator<>("Descrição", new NaoBrancoValidator()), this.description)
                .add(new NamedValidator<>("Descrição", new CaracteresEspeciaisValidator()), this.description)
                .add(new NamedValidator<>("Descrição", new TamanhoMinimoValidator(3)), this.description)
                .add(new NamedValidator<>("Descrição", new TamanhoMaximoValidator(50)), this.description)
                .add(new NamedValidator<>("Implementação", new ObjetoNaoNuloValidator()), this.implementation)
                .add(new NamedValidator<>("Implementação", new DataFuturaValidator()), this.implementation)
                .add(new NamedValidator<>("Data final", new ObjetoNaoNuloValidator()), this.finalDate)
                .add(new NamedValidator<>("Data final", new DataFuturaValidator()), this.finalDate)
                .validate();
    }
}
