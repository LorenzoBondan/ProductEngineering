package br.com.todeschini.domain.business.packaging.usedpolyethylene;

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
public class DUsedPolyethylene extends DBaseUsedMaterial {

    private Long polyethyleneCode;
    private String ghostCode;

    public DUsedPolyethylene(Long id){
        this.setId(id);
    }

    @Override
    public void validate(){
        super.validate();
        new ValidationBuilder()
                .add(new NamedValidator<>("Código do Fantasma", new ObjetoNaoNuloValidator()), this.ghostCode)
                .add(new NamedValidator<>("Código do Polietileno", new ObjetoNaoNuloValidator()), this.polyethyleneCode)
                .add(new NamedValidator<>("Código do Polietileno", new NumeroMaiorQueZeroValidator()), this.polyethyleneCode)
                .validate();
    }
}
