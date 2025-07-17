package br.com.todeschini.itemservicedomain.acessoriousado;

import br.com.todeschini.itemservicedomain.acessorio.DAcessorio;
import br.com.todeschini.itemservicedomain.filho.DFilho;
import br.com.todeschini.libauditdomain.enums.DSituacaoEnum;
import br.com.todeschini.libexceptionhandler.exceptions.ValidationException;
import br.com.todeschini.libvalidationhandler.util.FormatadorNumeros;
import br.com.todeschini.libvalidationhandler.validation.NamedValidator;
import br.com.todeschini.libvalidationhandler.validation.ValidationBuilder;
import br.com.todeschini.libvalidationhandler.validation.impl.NumeroMaiorOuIgualAZeroValidator;
import br.com.todeschini.libvalidationhandler.validation.impl.NumeroMaiorQueZeroValidator;
import br.com.todeschini.libvalidationhandler.validation.impl.ObjetoNaoNuloValidator;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DAcessorioUsado {

    @EqualsAndHashCode.Include
    private Integer codigo;
    private DAcessorio acessorio;
    private DFilho filho;
    private Integer quantidade;
    private Double valor;
    private String unidadeMedida;
    private DSituacaoEnum situacao;

    public DAcessorioUsado(Integer codigo){
        this.codigo = codigo;
    }

    public void validar() throws ValidationException {
        new ValidationBuilder()
                .add(new NamedValidator<>("Acessório", new ObjetoNaoNuloValidator()), this.acessorio)
                .add(new NamedValidator<>("Filho", new ObjetoNaoNuloValidator()), this.acessorio)
                .add(new NamedValidator<>("Quantidade", new ObjetoNaoNuloValidator()), this.quantidade)
                .add(new NamedValidator<>("Quantidade", new NumeroMaiorQueZeroValidator()), this.quantidade)
                .add(new NamedValidator<>("Valor", new NumeroMaiorOuIgualAZeroValidator()), this.valor)
                .validate();
    }

    public Double calcularValor() {
        this.setValor(FormatadorNumeros.formatarValor(this.getAcessorio().getValor()) * quantidade);
        return this.getValor();
    }
}
