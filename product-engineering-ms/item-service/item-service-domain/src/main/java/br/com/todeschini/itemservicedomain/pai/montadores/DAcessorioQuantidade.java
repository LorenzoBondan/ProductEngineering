package br.com.todeschini.itemservicedomain.pai.montadores;

import br.com.todeschini.itemservicedomain.acessorio.DAcessorio;
import br.com.todeschini.libexceptionhandler.exceptions.ValidationException;
import br.com.todeschini.libvalidationhandler.validation.NamedValidator;
import br.com.todeschini.libvalidationhandler.validation.ValidationBuilder;
import br.com.todeschini.libvalidationhandler.validation.impl.NumeroMaiorQueZeroValidator;
import br.com.todeschini.libvalidationhandler.validation.impl.ObjetoNaoNuloValidator;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DAcessorioQuantidade {

    private DAcessorio acessorio;
    private Integer quantidade;

    public void validar() throws ValidationException {
        new ValidationBuilder()
                .add(new NamedValidator<>("Acessório", new ObjetoNaoNuloValidator()), this.acessorio)
                .add(new NamedValidator<>("Quantidade", new ObjetoNaoNuloValidator()), this.quantidade)
                .add(new NamedValidator<>("Quantidade", new NumeroMaiorQueZeroValidator()), this.quantidade)
                .validate();
        acessorio.validar();
    }
}
