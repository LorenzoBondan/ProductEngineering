package br.com.todeschini.domain.business.packaging.usednonwovenfabric;

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
public class DUsedNonwovenFabric extends DBaseUsedMaterial {

    private Long nonwovenFabricCode;
    private String ghostCode;

    public DUsedNonwovenFabric(Long id){
        this.setId(id);
    }

    @Override
    public void validate(){
        super.validate();
        new ValidationBuilder()
                .add(new NamedValidator<>("Código do Fantasma", new ObjetoNaoNuloValidator()), this.ghostCode)
                .add(new NamedValidator<>("Código do Poliéster", new ObjetoNaoNuloValidator()), this.nonwovenFabricCode)
                .add(new NamedValidator<>("Código do Poliéster", new NumeroMaiorQueZeroValidator()), this.nonwovenFabricCode)
                .validate();
    }
}
