package br.com.todeschini.domain.business.configurator.configurators.modulationconfigurator;

import br.com.todeschini.domain.business.configurator.DBaseGhostConfigurator;
import br.com.todeschini.domain.configurator.ModulationConfigurator;
import br.com.todeschini.domain.validation.NamedValidator;
import br.com.todeschini.domain.validation.ValidationBuilder;
import br.com.todeschini.domain.validation.impl.DataFuturaValidator;
import br.com.todeschini.domain.validation.impl.NumeroMaiorQueZeroValidator;
import br.com.todeschini.domain.validation.impl.ObjetoNaoNuloValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DModulationConfigurator extends DBaseGhostConfigurator {

    private ModulationConfigurator config;
    private String ghostSuffix;
    private Long glueCode;
    private LocalDate implementation;

    public DModulationConfigurator(ModulationConfigurator config, String ghostSuffix, Long glueCode, Long cornerBracketCode, Long plasticCode, Long nonwovenFabricCode, Long polyethyleneCode, Boolean upper, Double additional, Integer width, Integer quantity, Boolean oneFace, LocalDate implementation) {
        this.config = config;
        this.ghostSuffix = ghostSuffix;
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
                .add(new NamedValidator<>("Código da Cola", new ObjetoNaoNuloValidator()), this.glueCode)
                .add(new NamedValidator<>("Código da Cola", new NumeroMaiorQueZeroValidator()), this.glueCode)
                .add(new NamedValidator<>("Implementação", new ObjetoNaoNuloValidator()), this.implementation)
                .add(new NamedValidator<>("Implementação", new DataFuturaValidator()), this.implementation)
                .validate();
    }
}
