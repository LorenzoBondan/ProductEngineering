package br.com.todeschini.domain.business.mdf.paintingtype;

import br.com.todeschini.domain.exceptions.ValidationException;
import br.com.todeschini.domain.validation.NamedValidator;
import br.com.todeschini.domain.validation.ValidationBuilder;
import br.com.todeschini.domain.validation.impl.NaoBrancoValidator;
import br.com.todeschini.domain.validation.impl.ObjetoNaoNuloValidator;
import br.com.todeschini.domain.validation.impl.TamanhoMaximoValidator;
import br.com.todeschini.domain.validation.impl.TamanhoMinimoValidator;
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

    public DPaintingType(Long id){
        this.id = id;
    }

    public void validate() throws ValidationException {
        new ValidationBuilder()
                .add(new NamedValidator<>("Descrição", new ObjetoNaoNuloValidator()), this.description)
                .add(new NamedValidator<>("Descrição", new NaoBrancoValidator()), this.description)
                .add(new NamedValidator<>("Descrição", new TamanhoMinimoValidator(3)), this.description)
                .add(new NamedValidator<>("Descrição", new TamanhoMaximoValidator(30)), this.description)
                .validate();
    }
}
