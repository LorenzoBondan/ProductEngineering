package br.com.todeschini.domain.business.packaging.usedplastic;

import br.com.todeschini.domain.business.basedomains.DBaseUsedMaterial;
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
public class DUsedPlastic extends DBaseUsedMaterial {

    private Long plasticCode;
    private String ghostCode;

    public DUsedPlastic(Long id){
        this.setId(id);
    }

    @Override
    public void validate(){
        super.validate();
        new ValidationBuilder()
                .add(new NamedValidator<>("Código do Fantasma", new ObjetoNaoNuloValidator()), this.ghostCode)
                .add(new NamedValidator<>("Código do Plástico", new ObjetoNaoNuloValidator()), this.plasticCode)
                .add(new NamedValidator<>("Código do Plástico", new NumeroMaiorQueZeroValidator()), this.plasticCode)
                .validate();
    }
}
