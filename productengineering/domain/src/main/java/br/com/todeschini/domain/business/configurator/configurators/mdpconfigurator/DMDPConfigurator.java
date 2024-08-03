package br.com.todeschini.domain.business.configurator.configurators.mdpconfigurator;

import br.com.todeschini.domain.business.configurator.DBaseGhostConfigurator;
import br.com.todeschini.domain.configurator.BPConfigurator;
import br.com.todeschini.domain.validation.NamedValidator;
import br.com.todeschini.domain.validation.ValidationBuilder;
import br.com.todeschini.domain.validation.impl.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DMDPConfigurator extends DBaseGhostConfigurator {

    private BPConfigurator config;
    private String ghostSuffix;
    private Integer edgeLength;
    private Integer edgeWidth;
    private Long glueCode;
    private LocalDate implementation;

    public DMDPConfigurator(BPConfigurator config, String ghostSuffix, Integer edgeLength, Integer edgeWidth, Long glueCode, Long cornerBracketCode, Long plasticCode, Long nonwovenFabricCode, Long polyethyleneCode, Boolean upper, Double additional, Integer width, Integer quantity, Boolean oneFace, LocalDate implementation) {
        this.config = config;
        this.ghostSuffix = ghostSuffix;
        this.edgeLength = edgeLength;
        this.edgeWidth = edgeWidth;
        this.glueCode = glueCode;
        this.implementation = implementation;
        this.setCornerBracketCode(cornerBracketCode);
        this.setPlasticCode(plasticCode);
        this.setNonwovenFabricCode(nonwovenFabricCode);
        this.setPolyethyleneCode(polyethyleneCode);
        this.setUpper(upper);
        this.setAdditional(additional);
        this.setWidth(width);
        this.setQuantity(quantity);
        this.setOneFace(oneFace);
    }

    public void validate(){
        super.validate();
        new ValidationBuilder()
                .add(new NamedValidator<>("Configuração", new ObjetoNaoNuloValidator()), this.config)
                .add(new NamedValidator<>("Sufixo do Fantasma", new ObjetoNaoNuloValidator()), this.ghostSuffix)
                .add(new NamedValidator<>("Sufixo do Fantasma", new CaracteresEspeciaisValidator()), this.ghostSuffix)
                .add(new NamedValidator<>("Bordas no comprimento", new ObjetoNaoNuloValidator()), this.edgeLength)
                .add(new NamedValidator<>("Bordas no comprimento", new NumeroMaiorOuIgualAZeroValidator()), this.edgeLength)
                .add(new NamedValidator<>("Bordas na largura", new ObjetoNaoNuloValidator()), this.edgeWidth)
                .add(new NamedValidator<>("Bordas na largura", new NumeroMaiorOuIgualAZeroValidator()), this.edgeWidth)
                .add(new NamedValidator<>("Código da Cola", new ObjetoNaoNuloValidator()), this.glueCode)
                .add(new NamedValidator<>("Código da Cola", new NumeroMaiorQueZeroValidator()), this.glueCode)
                .add(new NamedValidator<>("Implementação", new ObjetoNaoNuloValidator()), this.implementation)
                .add(new NamedValidator<>("Implementação", new DataFuturaValidator()), this.implementation)
                .validate();
    }
}
