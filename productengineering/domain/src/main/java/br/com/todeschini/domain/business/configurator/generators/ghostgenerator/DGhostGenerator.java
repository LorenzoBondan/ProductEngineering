package br.com.todeschini.domain.business.configurator.generators.ghostgenerator;

import br.com.todeschini.domain.business.configurator.DBaseGhostConfigurator;
import br.com.todeschini.domain.validation.NamedValidator;
import br.com.todeschini.domain.validation.ValidationBuilder;
import br.com.todeschini.domain.validation.impl.ObjetoNaoNuloValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DGhostGenerator extends DBaseGhostConfigurator {

    private Object ghost;

    public DGhostGenerator(Object ghost, Long cornerBracketCode, Long plasticCode, Long nonwovenFabricCode, Long polyethyleneCode, Boolean upper, Double additional, Integer width, Integer quantity, Boolean oneFace) {
        this.ghost = ghost;
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
                .add(new NamedValidator<>("Fantasma", new ObjetoNaoNuloValidator()), this.ghost)
                .validate();
    }
}
