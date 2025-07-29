package br.com.todeschini.mdpservicedomain.chapa;

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
public class DChapa extends DMaterial  {

    private Integer espessura;
    private Integer faces;

    public DChapa(Integer codigo){
        this.setCodigo(codigo);
    }

    @Override
    public void validar() throws ValidationException {
        super.validar();
        new ValidationBuilder()
                .add(new NamedValidator<>("Espessura", new ObjetoNaoNuloValidator()), this.espessura)
                .add(new NamedValidator<>("Espessura", new NumeroMaiorQueZeroValidator()), this.espessura)
                .add(new NamedValidator<>("Faces", new ObjetoNaoNuloValidator()), this.faces)
                .add(new NamedValidator<>("Faces", new NumeroMaiorQueZeroValidator()), this.faces)
                .validate();
    }
}
