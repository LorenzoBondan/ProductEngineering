package br.com.todeschini.domain.business.guides.machine;

import br.com.todeschini.domain.exceptions.ValidationException;
import br.com.todeschini.domain.validation.NamedValidator;
import br.com.todeschini.domain.validation.ValidationBuilder;
import br.com.todeschini.domain.validation.impl.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DMachine {

    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    private String[] formula;
    private Double value;
    private Long machineGroupId;
    private List<Long> guideMachinesIds = new ArrayList<>();

    public DMachine(Long id){
        this.id = id;
    }

    public void validate() throws ValidationException {
        new ValidationBuilder()
                .add(new NamedValidator<>("Nome", new ObjetoNaoNuloValidator()), this.name)
                .add(new NamedValidator<>("Nome", new NaoBrancoValidator()), this.name)
                .add(new NamedValidator<>("Nome", new TamanhoMinimoValidator(3)), this.name)
                .add(new NamedValidator<>("Nome", new TamanhoMaximoValidator(30)), this.name)
                .add(new NamedValidator<>("Grupo", new ObjetoNaoNuloValidator()), this.machineGroupId)
                .add(new NamedValidator<>("Valor", new ObjetoNaoNuloValidator()), this.value)
                .add(new NamedValidator<>("Valor", new NumeroMaiorQueZeroValidator()), this.value)
                .validate();
    }
}
