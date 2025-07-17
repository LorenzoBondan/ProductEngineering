package br.com.todeschini.itemservicedomain.medidas;

import br.com.todeschini.libauditdomain.enums.DSituacaoEnum;
import br.com.todeschini.libexceptionhandler.exceptions.ValidationException;
import br.com.todeschini.libvalidationhandler.validation.NamedValidator;
import br.com.todeschini.libvalidationhandler.validation.ValidationBuilder;
import br.com.todeschini.libvalidationhandler.validation.impl.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DMedidas {

    @EqualsAndHashCode.Include
    private Integer codigo;
    private Integer altura;
    private Integer largura;
    private Integer espessura;
    private DSituacaoEnum situacao;

    public DMedidas(Integer codigo){
        this.codigo = codigo;
    }

    public void validar() throws ValidationException {
        new ValidationBuilder()
                .add(new NamedValidator<>("Altura", new ObjetoNaoNuloValidator()), this.altura)
                .add(new NamedValidator<>("Altura", new NumeroMaiorQueZeroValidator()), this.altura)
                .add(new NamedValidator<>("Largura", new ObjetoNaoNuloValidator()), this.largura)
                .add(new NamedValidator<>("Largura", new NumeroMaiorQueZeroValidator()), this.largura)
                .add(new NamedValidator<>("Espessura", new ObjetoNaoNuloValidator()), this.espessura)
                .add(new NamedValidator<>("Espessura", new NumeroMaiorQueZeroValidator()), this.espessura)
                .validate();
    }
}
