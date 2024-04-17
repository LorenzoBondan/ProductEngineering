package br.com.todeschini.domain.business.basedomains;

import br.com.todeschini.domain.exceptions.ValidationException;
import br.com.todeschini.domain.validation.NamedValidator;
import br.com.todeschini.domain.validation.ValidationBuilder;
import br.com.todeschini.domain.validation.impl.NumeroMaiorQueZeroValidator;
import br.com.todeschini.domain.validation.impl.ObjetoNaoNuloValidator;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DBaseUsedMaterial {

    @EqualsAndHashCode.Include
    private Long id;
    private Double netQuantity;
    private Double grossQuantity;
    private String measurementUnit;

    public void validate() throws ValidationException {
        new ValidationBuilder()
                .add(new NamedValidator<>("Quantidade líquida", new ObjetoNaoNuloValidator()), this.netQuantity)
                .add(new NamedValidator<>("Quantidade líquida", new NumeroMaiorQueZeroValidator()), this.netQuantity)
                .add(new NamedValidator<>("Quantidade bruta", new ObjetoNaoNuloValidator()), this.grossQuantity)
                .add(new NamedValidator<>("Quantidade bruta", new NumeroMaiorQueZeroValidator()), this.grossQuantity)
                .validate();
    }
}
