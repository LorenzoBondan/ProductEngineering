package br.com.todeschini.itemservicedomain.pai.montadores;

import br.com.todeschini.itemservicedomain.medidas.DMedidas;
import br.com.todeschini.itemservicedomain.pai.DPai;
import br.com.todeschini.itemservicedomain.roteiroservice.DMaquina;
import br.com.todeschini.libexceptionhandler.exceptions.ValidationException;
import br.com.todeschini.libvalidationhandler.validation.NamedValidator;
import br.com.todeschini.libvalidationhandler.validation.ValidationBuilder;
import br.com.todeschini.libvalidationhandler.validation.impl.ObjetoNaoNuloValidator;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DItemModulacao {

    private DPai pai;
    private DMedidas medidas;
    private List<DMaquina> maquinas = new ArrayList<>();

    public void validar() throws ValidationException {
        new ValidationBuilder()
                .add(new NamedValidator<>("Pai", new ObjetoNaoNuloValidator()), this.pai)
                .add(new NamedValidator<>("Medidas", new ObjetoNaoNuloValidator()), this.medidas)
                .validate();
        pai.validar();
        medidas.validar();
    }
}
