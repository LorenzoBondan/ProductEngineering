package br.com.todeschini.domain.business.aluminium.usedscrew;

import br.com.todeschini.domain.business.basedomains.DBaseUsedAluminiumMaterial;
import br.com.todeschini.domain.validation.NamedValidator;
import br.com.todeschini.domain.validation.ValidationBuilder;
import br.com.todeschini.domain.validation.impl.NumeroMaiorQueZeroValidator;
import br.com.todeschini.domain.validation.impl.ObjetoNaoNuloValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DUsedScrew extends DBaseUsedAluminiumMaterial {

    private Long screwCode;

    public DUsedScrew(Long id){
        this.setId(id);
    }

    @Override
    public void validate(){
        super.validate();
        new ValidationBuilder()
                .add(new NamedValidator<>("Código do Parafuso", new ObjetoNaoNuloValidator()), this.screwCode)
                .add(new NamedValidator<>("Código do Parafuso", new NumeroMaiorQueZeroValidator()), this.screwCode)
                .validate();
    }
}
