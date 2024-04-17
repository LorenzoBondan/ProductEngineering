package br.com.todeschini.domain.business.aluminium.usedtrysquare;

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
public class DUsedTrySquare extends DBaseUsedAluminiumMaterial {

    private Long trySquareCode;

    public DUsedTrySquare(Long id){
        this.setId(id);
    }

    @Override
    public void validate(){
        super.validate();
        new ValidationBuilder()
                .add(new NamedValidator<>("Código da Esquadreta", new ObjetoNaoNuloValidator()), this.trySquareCode)
                .add(new NamedValidator<>("Código da Esquadreta", new NumeroMaiorQueZeroValidator()), this.trySquareCode)
                .validate();
    }
}
