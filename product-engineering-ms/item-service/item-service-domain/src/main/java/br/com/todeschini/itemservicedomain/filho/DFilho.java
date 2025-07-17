package br.com.todeschini.itemservicedomain.filho;

import br.com.todeschini.itemservicedomain.acessoriousado.DAcessorioUsado;
import br.com.todeschini.itemservicedomain.cor.DCor;
import br.com.todeschini.itemservicedomain.enums.DTipoFilhoEnum;
import br.com.todeschini.itemservicedomain.materialusado.DMaterialUsado;
import br.com.todeschini.itemservicedomain.medidas.DMedidas;
import br.com.todeschini.itemservicedomain.pai.DPai;
import br.com.todeschini.itemservicedomain.roteiroservice.DRoteiro;
import br.com.todeschini.libauditdomain.enums.DSituacaoEnum;
import br.com.todeschini.libexceptionhandler.exceptions.ValidationException;
import br.com.todeschini.libvalidationhandler.util.FormatadorNumeros;
import br.com.todeschini.libvalidationhandler.validation.NamedValidator;
import br.com.todeschini.libvalidationhandler.validation.ValidationBuilder;
import br.com.todeschini.libvalidationhandler.validation.impl.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DFilho {

    @EqualsAndHashCode.Include
    private Integer codigo;
    private String descricao;
    private DPai pai;
    private DCor cor;
    private DMedidas medidas;
    private DRoteiro roteiro;
    private String unidadeMedida;
    private LocalDate implantacao;
    private Double valor;
    private DTipoFilhoEnum tipo;
    private DSituacaoEnum situacao;

    private List<DFilho> filhos = new ArrayList<>();
    private List<DMaterialUsado> materiaisUsados = new ArrayList<>();
    private List<DAcessorioUsado> acessoriosUsados = new ArrayList<>();

    public DFilho(Integer codigo){
        this.codigo = codigo;
    }

    public DFilho(Integer codigo, String descricao, DCor cor, DMedidas medidas){
        this.codigo = codigo;
        this.descricao = descricao;
        this.cor = cor;
        this.medidas = medidas;
    }

    public void validar() throws ValidationException {
        new ValidationBuilder()
                .add(new NamedValidator<>("Descrição", new ObjetoNaoNuloValidator()), this.descricao)
                .add(new NamedValidator<>("Descrição", new NaoBrancoValidator()), this.descricao)
                .add(new NamedValidator<>("Descrição", new CaracteresEspeciaisValidator()), this.descricao)
                .add(new NamedValidator<>("Descrição", new TamanhoMinimoValidator(3)), this.descricao)
                .add(new NamedValidator<>("Descrição", new TamanhoMaximoValidator(50)), this.descricao)
                .add(new NamedValidator<>("Cor", new ObjetoNaoNuloValidator()), this.cor)
                .add(new NamedValidator<>("Medidas", new ObjetoNaoNuloValidator()), this.medidas)
                .add(new NamedValidator<>("Implantação", new DataFuturaValidator()), this.implantacao)
                .add(new NamedValidator<>("Valor", new NumeroMaiorOuIgualAZeroValidator()), this.valor)
                .add(new NamedValidator<>("Tipo", new ObjetoNaoNuloValidator()), this.tipo)
                .validate();
    }

    public Double calcularValor(){
        double valor = 0.0;
        for(DFilho filho : filhos){
            valor += filho.calcularValor();
        }
        for(DMaterialUsado materialUsado : materiaisUsados){
            valor += materialUsado.calcularValor();
        }
        for(DAcessorioUsado acessorioUsado : acessoriosUsados){
            valor += acessorioUsado.calcularValor();
        }
        if(roteiro != null){
            valor += roteiro.calcularValor();
        }
        valor = FormatadorNumeros.formatarValor(valor);
        this.setValor(valor);
        return valor;
    }
}
