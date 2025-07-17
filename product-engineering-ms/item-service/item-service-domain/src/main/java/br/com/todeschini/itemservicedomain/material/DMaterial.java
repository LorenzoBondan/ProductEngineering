package br.com.todeschini.itemservicedomain.material;

import br.com.todeschini.itemservicedomain.cor.DCor;
import br.com.todeschini.itemservicedomain.enums.DTipoMaterialEnum;
import br.com.todeschini.libauditdomain.enums.DSituacaoEnum;
import br.com.todeschini.libexceptionhandler.exceptions.ValidationException;
import br.com.todeschini.libvalidationhandler.validation.NamedValidator;
import br.com.todeschini.libvalidationhandler.validation.ValidationBuilder;
import br.com.todeschini.libvalidationhandler.validation.impl.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DMaterial {

    @EqualsAndHashCode.Include
    private Integer codigo;
    private String descricao;
    private DTipoMaterialEnum tipoMaterial;
    private LocalDate implantacao;
    private Double porcentagemPerda;
    private Double valor;
    private DCor cor;
    private DSituacaoEnum situacao;

    public DMaterial(Integer codigo){
        this.codigo = codigo;
    }

    public void validar() throws ValidationException {
        new ValidationBuilder()
                .add(new NamedValidator<>("Descrição", new ObjetoNaoNuloValidator()), this.descricao)
                .add(new NamedValidator<>("Descrição", new NaoBrancoValidator()), this.descricao)
                .add(new NamedValidator<>("Descrição", new CaracteresEspeciaisValidator()), this.descricao)
                .add(new NamedValidator<>("Descrição", new TamanhoMinimoValidator(3)), this.descricao)
                .add(new NamedValidator<>("Descrição", new TamanhoMaximoValidator(50)), this.descricao)
                .add(new NamedValidator<>("Tipo material", new ObjetoNaoNuloValidator()), this.tipoMaterial)
                //.add(new NamedValidator<>("Implantação", new DataFuturaValidator()), this.implantacao)
                .add(new NamedValidator<>("Porcentagem de perda", new ObjetoNaoNuloValidator()), this.porcentagemPerda)
                .add(new NamedValidator<>("Porcentagem de perda", new NumeroMaiorOuIgualAZeroValidator()), this.porcentagemPerda)
                .add(new NamedValidator<>("Valor", new ObjetoNaoNuloValidator()), this.valor)
                .add(new NamedValidator<>("Valor", new NumeroMaiorOuIgualAZeroValidator()), this.valor)
                .validate();
    }
}
