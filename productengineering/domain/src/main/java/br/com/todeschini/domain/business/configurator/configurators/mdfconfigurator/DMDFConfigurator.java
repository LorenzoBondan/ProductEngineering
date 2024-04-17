package br.com.todeschini.domain.business.configurator.configurators.mdfconfigurator;

import br.com.todeschini.domain.business.configurator.DBaseGhostConfigurator;
import br.com.todeschini.domain.configurator.BPConfigurator;
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
public class DMDFConfigurator extends DBaseGhostConfigurator {

    private BPConfigurator config;
    private String ghostSuffix;
    private Boolean satin;
    private Boolean highShine;
    private Boolean satinGlass;
    private Integer faces;
    private Boolean special;
    private Long paintingBorderBackgroundCode;
    private Long polyesterCode;
    private LocalDate implementation;

    public DMDFConfigurator(BPConfigurator config, String ghostSuffix, Boolean satin, Boolean highShine, Boolean satinGlass, Integer faces, Boolean special, Long paintingBorderBackgroundCode, Long polyesterCode, Long cornerBracketCode, Long plasticCode, Long nonwovenFabricCode, Long polyethyleneCode, Boolean upper, Double additional, Integer width, Integer quantity, Boolean oneFace, LocalDate implementation) {
        this.config = config;
        this.ghostSuffix = ghostSuffix;
        this.satin = satin;
        this.highShine = highShine;
        this.satinGlass = satinGlass;
        this.faces = faces;
        this.special = special;
        this.paintingBorderBackgroundCode = paintingBorderBackgroundCode;
        this.polyesterCode = polyesterCode;
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
                .add(new NamedValidator<>("Acetinada", new ObjetoNaoNuloValidator()), this.satin)
                .add(new NamedValidator<>("Acetinada Vidro", new ObjetoNaoNuloValidator()), this.satinGlass)
                .add(new NamedValidator<>("Alto Brilho", new ObjetoNaoNuloValidator()), this.highShine)
                .add(new NamedValidator<>("Faces", new ObjetoNaoNuloValidator()), this.faces)
                .add(new NamedValidator<>("Faces", new NumeroMaiorQueZeroValidator()), this.faces)
                .add(new NamedValidator<>("Especial", new ObjetoNaoNuloValidator()), this.special)
                .add(new NamedValidator<>("Implementação", new ObjetoNaoNuloValidator()), this.implementation)
                .add(new NamedValidator<>("Implementação", new DataFuturaValidator()), this.implementation)
                .validate();
    }
}
