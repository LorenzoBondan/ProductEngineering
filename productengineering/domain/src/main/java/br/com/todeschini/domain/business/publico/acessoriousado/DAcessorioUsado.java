package br.com.todeschini.domain.business.publico.acessoriousado;

import br.com.todeschini.domain.Descritivel;
import br.com.todeschini.domain.business.enums.DSituacao;
import br.com.todeschini.domain.business.publico.acessorio.DAcessorio;
import br.com.todeschini.domain.business.publico.cor.DCor;
import br.com.todeschini.domain.business.publico.filho.DFilho;
import br.com.todeschini.domain.business.publico.medidas.DMedidas;
import br.com.todeschini.domain.exceptions.ValidationException;
import br.com.todeschini.domain.metadata.BatchEditable;
import br.com.todeschini.domain.metadata.Domain;
import br.com.todeschini.domain.util.FormatadorNumeros;
import br.com.todeschini.domain.validation.NamedValidator;
import br.com.todeschini.domain.validation.ValidationBuilder;
import br.com.todeschini.domain.validation.impl.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Domain
public class DAcessorioUsado implements Descritivel {

    @EqualsAndHashCode.Include
    private Integer codigo;
    private DAcessorio acessorio;
    private DFilho filho;
    private Integer quantidade;
    private Double valor;
    private String unidadeMedida;
    private DSituacao situacao;

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

    @Override
    public String getDescricao() {
        return null;
    }
}
