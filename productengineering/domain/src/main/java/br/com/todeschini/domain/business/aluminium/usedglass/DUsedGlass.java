package br.com.todeschini.domain.business.aluminium.usedglass;

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
public class DUsedGlass extends DBaseUsedAluminiumMaterial {

    private Long glassCode;

    public DUsedGlass(Long id){
        this.setId(id);
    }

    @Override
    public void validate(){
        super.validate();
        new ValidationBuilder()
                .add(new NamedValidator<>("Código do Vidro", new ObjetoNaoNuloValidator()), this.glassCode)
                .add(new NamedValidator<>("Código do Vidro", new NumeroMaiorQueZeroValidator()), this.glassCode)
                .validate();
    }
}
