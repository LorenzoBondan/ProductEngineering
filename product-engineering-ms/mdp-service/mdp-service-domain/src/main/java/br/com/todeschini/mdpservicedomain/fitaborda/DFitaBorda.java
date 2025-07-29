package br.com.todeschini.mdpservicedomain.fitaborda;

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
public class DFitaBorda extends DMaterial {

    private Integer altura;
    private Integer espessura;

    public DFitaBorda(Integer codigo){
        this.setCodigo(codigo);
    }

    @Override
    public void validar() throws ValidationException {
        super.validar();
        new ValidationBuilder()
                .add(new NamedValidator<>("Altura", new ObjetoNaoNuloValidator()), this.altura)
                .add(new NamedValidator<>("Altura", new NumeroMaiorQueZeroValidator()), this.altura)
                .add(new NamedValidator<>("Espessura", new ObjetoNaoNuloValidator()), this.espessura)
                .add(new NamedValidator<>("Espessura", new NumeroMaiorQueZeroValidator()), this.espessura)
                .validate();
    }
}
