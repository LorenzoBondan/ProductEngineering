package br.com.todeschini.domain.business.configurator;

import br.com.todeschini.domain.validation.NamedValidator;
import br.com.todeschini.domain.validation.ValidationBuilder;
import br.com.todeschini.domain.validation.impl.NumeroMaiorOuIgualAZeroValidator;
import br.com.todeschini.domain.validation.impl.NumeroMaiorQueZeroValidator;
import br.com.todeschini.domain.validation.impl.ObjetoNaoNuloValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DBaseGhostConfigurator {

    private Long cornerBracketCode;
    private Long plasticCode;
    private Long nonwovenFabricCode;
    private Long polyethyleneCode;
    private Boolean upper;
    private Double additional;
    private Integer width;
    private Integer quantity;
    private Boolean oneFace;

    public void validate(){
        new ValidationBuilder()
                .add(new NamedValidator<>("Código da cantoneira", new ObjetoNaoNuloValidator()), this.cornerBracketCode)
                .add(new NamedValidator<>("Código da cantoneira", new NumeroMaiorQueZeroValidator()), this.cornerBracketCode)
                .add(new NamedValidator<>("Código do plástico", new ObjetoNaoNuloValidator()), this.plasticCode)
                .add(new NamedValidator<>("Código do plástico", new NumeroMaiorQueZeroValidator()), this.plasticCode)
                .add(new NamedValidator<>("Código do TNT", new ObjetoNaoNuloValidator()), this.nonwovenFabricCode)
                .add(new NamedValidator<>("Código do TNT", new NumeroMaiorQueZeroValidator()), this.nonwovenFabricCode)
                .add(new NamedValidator<>("Código do Polietileno", new ObjetoNaoNuloValidator()), this.polyethyleneCode)
                .add(new NamedValidator<>("Código do Polietileno", new NumeroMaiorQueZeroValidator()), this.polyethyleneCode)
                .add(new NamedValidator<>("Acima", new ObjetoNaoNuloValidator()), this.upper)
                .add(new NamedValidator<>("Adicional", new ObjetoNaoNuloValidator()), this.additional)
                .add(new NamedValidator<>("Adicional", new NumeroMaiorOuIgualAZeroValidator()), this.additional)
                .add(new NamedValidator<>("Largura", new ObjetoNaoNuloValidator()), this.width)
                .add(new NamedValidator<>("Largura", new NumeroMaiorOuIgualAZeroValidator()), this.width)
                .add(new NamedValidator<>("Quantidade", new ObjetoNaoNuloValidator()), this.quantity)
                .add(new NamedValidator<>("Quantidade", new NumeroMaiorOuIgualAZeroValidator()), this.quantity)
                .add(new NamedValidator<>("Uma face", new ObjetoNaoNuloValidator()), this.oneFace)
                .validate();
    }
}
