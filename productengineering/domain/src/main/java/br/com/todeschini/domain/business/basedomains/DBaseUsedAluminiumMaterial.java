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
public class DBaseUsedAluminiumMaterial {

    @EqualsAndHashCode.Include
    private Long id;
    private Double quantity;
    private String measurementUnit;
    private Long aluminiumSonCode;

    public void validate() throws ValidationException {
        new ValidationBuilder()
                .add(new NamedValidator<>("Quantidade", new ObjetoNaoNuloValidator()), this.quantity)
                .add(new NamedValidator<>("Quantidade", new NumeroMaiorQueZeroValidator()), this.quantity)
                .add(new NamedValidator<>("Código do Filho", new ObjetoNaoNuloValidator()), this.aluminiumSonCode)
                .add(new NamedValidator<>("Código do Filho", new NumeroMaiorQueZeroValidator()), this.aluminiumSonCode)
                .validate();
    }
}
