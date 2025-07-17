package br.com.todeschini.itemservicedomain.pai.montadores;

import br.com.todeschini.itemservicedomain.categoriacomponente.DCategoriaComponente;
import br.com.todeschini.itemservicedomain.cor.DCor;
import br.com.todeschini.itemservicedomain.enums.DTipoFilhoEnum;
import br.com.todeschini.itemservicedomain.enums.DTipoPinturaEnum;
import br.com.todeschini.itemservicedomain.material.DMaterial;
import br.com.todeschini.itemservicedomain.medidas.DMedidas;
import br.com.todeschini.itemservicedomain.modelo.DModelo;
import br.com.todeschini.itemservicedomain.roteiroservice.DMaquina;
import br.com.todeschini.libexceptionhandler.exceptions.ValidationException;
import br.com.todeschini.libvalidationhandler.validation.NamedValidator;
import br.com.todeschini.libvalidationhandler.validation.ValidationBuilder;
import br.com.todeschini.libvalidationhandler.validation.impl.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DMontadorEstruturaPai {

    private DModelo modelo;
    private DCategoriaComponente categoriaComponente;
    private List<DCor> cores;
    private List<DMedidas> medidas;
    private List<DMaterial> materiais;
    private List<DMaquina> maquinas;
    private LocalDate implantacao;
    private DTipoFilhoEnum tipoFilho;

    // fita borda
    private Integer bordasComprimento;
    private Integer bordasLargura;

    // variávaies da embalagem
    private Boolean plasticoAcima;
    private Double plasticoAdicional;
    private Integer larguraPlastico;
    private Integer numeroCantoneiras;
    private Boolean tntUmaFace;

    // pintura
    private DTipoPinturaEnum tipoPintura;
    private Integer faces;
    private Boolean especial;

    public void validar() throws ValidationException {
        new ValidationBuilder()
                .add(new NamedValidator<>("Modelo", new ObjetoNaoNuloValidator()), this.modelo)
                .add(new NamedValidator<>("Categoria Componente", new ObjetoNaoNuloValidator()), this.categoriaComponente)
                .add(new NamedValidator<>("Cores", new ObjetoNaoNuloValidator()), this.cores)
                .add(new NamedValidator<>("Medidas", new ObjetoNaoNuloValidator()), this.medidas)
                .add(new NamedValidator<>("Implantação", new DataFuturaValidator()), this.implantacao)
                .add(new NamedValidator<>("Tipo Filho", new ObjetoNaoNuloValidator()), this.tipoFilho)

                .add(new NamedValidator<>("Bordas Comprimento", new NumeroMaiorOuIgualAZeroValidator()), this.bordasComprimento)
                .add(new NamedValidator<>("Bordas Largura", new NumeroMaiorOuIgualAZeroValidator()), this.bordasLargura)

                .add(new NamedValidator<>("Plástico Acima", new ObjetoNaoNuloValidator()), this.plasticoAcima)
                .add(new NamedValidator<>("Plástico Adicional", new ObjetoNaoNuloValidator()), this.plasticoAdicional)
                .add(new NamedValidator<>("Plástico Adicional", new NumeroMaiorOuIgualAZeroValidator()), this.plasticoAdicional)
                .add(new NamedValidator<>("Largura do Plástico", new ObjetoNaoNuloValidator()), this.larguraPlastico)
                .add(new NamedValidator<>("Largura do Plástico", new NumeroMaiorOuIgualAZeroValidator()), this.larguraPlastico)
                .add(new NamedValidator<>("Número Cantoneiras", new ObjetoNaoNuloValidator()), this.numeroCantoneiras)
                .add(new NamedValidator<>("Número Cantoneiras", new NumeroMaiorOuIgualAZeroValidator()), this.numeroCantoneiras)
                .add(new NamedValidator<>("TNT uma face", new ObjetoNaoNuloValidator()), this.tntUmaFace)

                .add(new NamedValidator<>("Faces", new NumeroMaiorQueZeroValidator()), this.faces)
                .validate();
    }

    public DMontadorEstruturaPai(DModelo modelo, DCategoriaComponente categoriaComponente, List<DCor> cores, List<DMedidas> medidas, LocalDate implantacao, DTipoFilhoEnum tipoFilho){
        this.modelo = modelo;
        this.categoriaComponente = categoriaComponente;
        this.cores = cores;
        this.medidas = medidas;
        this.implantacao = implantacao;
        this.tipoFilho = tipoFilho;
    }
}
