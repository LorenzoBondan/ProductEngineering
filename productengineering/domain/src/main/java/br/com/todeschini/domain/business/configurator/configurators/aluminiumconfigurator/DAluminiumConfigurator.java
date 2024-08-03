package br.com.todeschini.domain.business.configurator.configurators.aluminiumconfigurator;

import br.com.todeschini.domain.business.configurator.DBaseGhostConfigurator;
import br.com.todeschini.domain.configurator.AluminiumConfigurator;
import br.com.todeschini.domain.validation.NamedValidator;
import br.com.todeschini.domain.validation.ValidationBuilder;
import br.com.todeschini.domain.validation.impl.CaracteresEspeciaisValidator;
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
public class DAluminiumConfigurator extends DBaseGhostConfigurator {

    private AluminiumConfigurator config;
    private Long aluminiumTypeId;
    private String ghostSuffix;
    private Long drawerPullCode;
    private Long trySquareCode;
    private Integer trySquareQuantity;
    private Long moldingCode;
    private Long glueCode;
    private LocalDate implementation;

    public DAluminiumConfigurator(AluminiumConfigurator config, Long aluminiumTypeId, String ghostSuffix, Long drawerPullCode, Long trySquareCode, Integer trySquareQuantity, Long moldingCode, Long glueCode, Long cornerBracketCode, Long plasticCode, Long nonwovenFabricCode, Long polyethyleneCode, Boolean upper, Double additional, Integer width, Integer quantity, Boolean oneFace, LocalDate implementation) {
        this.config = config;
        this.aluminiumTypeId = aluminiumTypeId;
        this.ghostSuffix = ghostSuffix;
        this.drawerPullCode = drawerPullCode;
        this.trySquareCode = trySquareCode;
        this.trySquareQuantity = trySquareQuantity;
        this.moldingCode = moldingCode;
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
                .add(new NamedValidator<>("Tipo de Alumínio", new ObjetoNaoNuloValidator()), this.aluminiumTypeId)
                .add(new NamedValidator<>("Código do Puxador", new ObjetoNaoNuloValidator()), this.drawerPullCode)
                .add(new NamedValidator<>("Código do Puxador", new NumeroMaiorQueZeroValidator()), this.drawerPullCode)
                .add(new NamedValidator<>("Código da Esquadreta", new ObjetoNaoNuloValidator()), this.trySquareCode)
                .add(new NamedValidator<>("Código da Esquadreta", new NumeroMaiorQueZeroValidator()), this.trySquareCode)
                .add(new NamedValidator<>("Quantidade da Esquadreta", new ObjetoNaoNuloValidator()), this.trySquareQuantity)
                .add(new NamedValidator<>("Quantidade da Esquadreta", new NumeroMaiorQueZeroValidator()), this.trySquareQuantity)
                .add(new NamedValidator<>("Código do Perfil", new ObjetoNaoNuloValidator()), this.moldingCode)
                .add(new NamedValidator<>("Código do Perfil", new NumeroMaiorQueZeroValidator()), this.moldingCode)
                .add(new NamedValidator<>("Código da Cola", new ObjetoNaoNuloValidator()), this.glueCode)
                .add(new NamedValidator<>("Código da Cola", new NumeroMaiorQueZeroValidator()), this.glueCode)
                .add(new NamedValidator<>("Implementação", new ObjetoNaoNuloValidator()), this.implementation)
                .add(new NamedValidator<>("Implementação", new DataFuturaValidator()), this.implementation)
                .validate();
    }
}
