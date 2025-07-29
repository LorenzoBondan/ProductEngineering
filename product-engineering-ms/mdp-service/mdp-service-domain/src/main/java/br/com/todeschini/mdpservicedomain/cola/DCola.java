package br.com.todeschini.mdpservicedomain.cola;

import br.com.todeschini.itemservicedomain.material.DMaterial;
import br.com.todeschini.libexceptionhandler.exceptions.ValidationException;
import br.com.todeschini.libvalidationhandler.validation.NamedValidator;
import br.com.todeschini.libvalidationhandler.validation.ValidationBuilder;
import br.com.todeschini.libvalidationhandler.validation.impl.NumeroMaiorQueZeroValidator;
import br.com.todeschini.libvalidationhandler.validation.impl.ObjetoNaoNuloValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DCola extends DMaterial {

    private Double gramatura;

    public DCola(Integer codigo){
        this.setCodigo(codigo);
    }

    @Override
    public void validar() throws ValidationException {
        super.validar();
        new ValidationBuilder()
                .add(new NamedValidator<>("Gramatura", new ObjetoNaoNuloValidator()), this.gramatura)
                .add(new NamedValidator<>("Gramatura", new NumeroMaiorQueZeroValidator()), this.gramatura)
                .validate();
    }
}
