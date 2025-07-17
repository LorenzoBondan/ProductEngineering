package br.com.todeschini.itemservicedomain.cor;

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
public class DCor {

    @EqualsAndHashCode.Include
    private Integer codigo;
    private String descricao;
    private String hexa;
    private DSituacaoEnum situacao;

    public DCor(Integer codigo){
        this.codigo = codigo;
    }

    public void validar() throws ValidationException {
        new ValidationBuilder()
                .add(new NamedValidator<>("Descrição", new ObjetoNaoNuloValidator()), this.descricao)
                .add(new NamedValidator<>("Descrição", new NaoBrancoValidator()), this.descricao)
                .add(new NamedValidator<>("Descrição", new CaracteresEspeciaisValidator()), this.descricao)
                .add(new NamedValidator<>("Descrição", new TamanhoMinimoValidator(3)), this.descricao)
                .add(new NamedValidator<>("Descrição", new TamanhoMaximoValidator(50)), this.descricao)
                .add(new NamedValidator<>("Hexa", new NaoBrancoValidator()), this.hexa)
                .add(new NamedValidator<>("Hexa", new HexaValidator()), this.hexa)
                .add(new NamedValidator<>("Hexa", new TamanhoMinimoValidator(3)), this.hexa)
                .add(new NamedValidator<>("Hexa", new TamanhoMaximoValidator(6)), this.hexa)
                .validate();
    }
}
