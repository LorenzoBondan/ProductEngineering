package br.com.todeschini.domain.business.mdf.paintingtype;

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
public class DPaintingType {

    @EqualsAndHashCode.Include
    private Long id;
    private String description;
    private DStatus status;

    public DPaintingType(Long id){
        this.id = id;
    }

    public void validate() throws ValidationException {
        new ValidationBuilder()
                .add(new NamedValidator<>("Descrição", new ObjetoNaoNuloValidator()), this.description)
                .add(new NamedValidator<>("Descrição", new NaoBrancoValidator()), this.description)
                .add(new NamedValidator<>("Descrição", new CaracteresEspeciaisValidator()), this.description)
                .add(new NamedValidator<>("Descrição", new TamanhoMinimoValidator(3)), this.description)
                .add(new NamedValidator<>("Descrição", new TamanhoMaximoValidator(30)), this.description)
                .validate();
    }
}
