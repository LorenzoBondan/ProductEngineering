package br.com.todeschini.domain.business.guides.machinegroup;

import br.com.todeschini.domain.business.enums.DStatus;
import br.com.todeschini.domain.exceptions.ValidationException;
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
public class DMachineGroup {

    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    private DStatus status;

    public DMachineGroup(Long id){
        this.id = id;
    }

    public void validate() throws ValidationException {
        new ValidationBuilder()
                .add(new NamedValidator<>("Nome", new ObjetoNaoNuloValidator()), this.name)
                .add(new NamedValidator<>("Nome", new NaoBrancoValidator()), this.name)
                .add(new NamedValidator<>("Nome", new CaracteresEspeciaisValidator()), this.name)
                .add(new NamedValidator<>("Nome", new TamanhoMinimoValidator(3)), this.name)
                .add(new NamedValidator<>("Nome", new TamanhoMaximoValidator(30)), this.name)
                .validate();
    }
}
