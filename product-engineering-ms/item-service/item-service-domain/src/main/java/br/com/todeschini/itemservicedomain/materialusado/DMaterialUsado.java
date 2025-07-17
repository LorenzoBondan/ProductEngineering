package br.com.todeschini.itemservicedomain.materialusado;

import br.com.todeschini.itemservicedomain.filho.DFilho;
import br.com.todeschini.itemservicedomain.material.DMaterial;
import br.com.todeschini.libauditdomain.enums.DSituacaoEnum;
import br.com.todeschini.libexceptionhandler.exceptions.ValidationException;
import br.com.todeschini.libvalidationhandler.util.FormatadorNumeros;
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
public class DMaterialUsado {

    @EqualsAndHashCode.Include
    private Integer codigo;
    private DFilho filho;
    private DMaterial material;
    private Double valor;
    private Double quantidadeLiquida;
    private Double quantidadeBruta;
    private String unidadeMedida;
    private DSituacaoEnum situacao;

    public DMaterialUsado(Integer codigo){
        this.codigo = codigo;
    }

    public void validar() throws ValidationException {
        new ValidationBuilder()
                .add(new NamedValidator<>("Filho", new ObjetoNaoNuloValidator()), this.filho)
                .add(new NamedValidator<>("Material", new ObjetoNaoNuloValidator()), this.material)
                .add(new NamedValidator<>("Valor", new NumeroMaiorOuIgualAZeroValidator()), this.valor)
                .add(new NamedValidator<>("Quantidade Líquida", new ObjetoNaoNuloValidator()), this.quantidadeLiquida)
                .add(new NamedValidator<>("Quantidade Líquida", new NumeroMaiorOuIgualAZeroValidator()), this.quantidadeLiquida)
                .add(new NamedValidator<>("Quantidade Bruta", new ObjetoNaoNuloValidator()), this.quantidadeBruta)
                .add(new NamedValidator<>("Quantidade Bruta", new NumeroMaiorOuIgualAZeroValidator()), this.quantidadeBruta)
                .validate();
    }

    public Double calcularQuantidadeLiquida(){
        return quantidadeLiquida;
    }

    public void calcularQuantidadeBruta(){
        double quantidadeBruta = quantidadeLiquida / (1.0 - this.getMaterial().getPorcentagemPerda() / 100);
        this.setQuantidadeBruta(FormatadorNumeros.formatarQuantidade(quantidadeBruta));
    }

    public Double calcularValor(){
        return FormatadorNumeros.formatarValor(this.getMaterial().getValor() * quantidadeLiquida);
    }
}
