package br.com.todeschini.itemservicedomain.pai;

import br.com.todeschini.itemservicedomain.categoriacomponente.DCategoriaComponente;
import br.com.todeschini.itemservicedomain.enums.DTipoPinturaEnum;
import br.com.todeschini.itemservicedomain.filho.DFilho;
import br.com.todeschini.itemservicedomain.modelo.DModelo;
import br.com.todeschini.libauditdomain.enums.DSituacaoEnum;
import br.com.todeschini.libexceptionhandler.exceptions.ValidationException;
import br.com.todeschini.libvalidationhandler.validation.NamedValidator;
import br.com.todeschini.libvalidationhandler.validation.ValidationBuilder;
import br.com.todeschini.libvalidationhandler.validation.impl.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DPai {

    @EqualsAndHashCode.Include
    private Integer codigo;
    private DModelo modelo;
    private DCategoriaComponente categoriaComponente;
    private String descricao;
    private Integer bordasComprimento;
    private Integer bordasLargura;
    private Integer numeroCantoneiras;
    private Boolean tntUmaFace;
    private Boolean plasticoAcima;
    private Double plasticoAdicional;
    private Integer larguraPlastico;
    private Integer faces;
    private Boolean especial;
    private DTipoPinturaEnum tipoPintura;
    private DSituacaoEnum situacao;

    private List<DFilho> filhos = new ArrayList<>();

    public DPai(Integer codigo){
        this.codigo = codigo;
    }

    public void validar() throws ValidationException {
        new ValidationBuilder()
                .add(new NamedValidator<>("Modelo", new ObjetoNaoNuloValidator()), this.modelo)
                .add(new NamedValidator<>("Categoria Componente", new ObjetoNaoNuloValidator()), this.categoriaComponente)
                .add(new NamedValidator<>("Descrição", new ObjetoNaoNuloValidator()), this.descricao)
                .add(new NamedValidator<>("Descrição", new NaoBrancoValidator()), this.descricao)
                .add(new NamedValidator<>("Descrição", new CaracteresEspeciaisValidator()), this.descricao)
                .add(new NamedValidator<>("Descrição", new TamanhoMinimoValidator(3)), this.descricao)
                .add(new NamedValidator<>("Descrição", new TamanhoMaximoValidator(50)), this.descricao)
                .add(new NamedValidator<>("Bordas Comprimento", new NumeroMaiorOuIgualAZeroValidator()), this.bordasComprimento)
                .add(new NamedValidator<>("Bordas Largura", new NumeroMaiorOuIgualAZeroValidator()), this.bordasLargura)
                .add(new NamedValidator<>("Número Cantoneiras", new NumeroMaiorOuIgualAZeroValidator()), this.numeroCantoneiras)
                .add(new NamedValidator<>("Plástico adicional", new NumeroMaiorOuIgualAZeroValidator()), this.plasticoAdicional)
                .add(new NamedValidator<>("Largura Plástico", new NumeroMaiorOuIgualAZeroValidator()), this.larguraPlastico)
                .add(new NamedValidator<>("Faces", new NumeroMaiorOuIgualAZeroValidator()), this.faces)

                .validate();
    }

    public void gerarDescricao(){
        this.setDescricao(categoriaComponente.getDescricao() + " " + modelo.getDescricao());
    }
}
