package br.com.todeschini.domain.business.packaging.usedcornerbracket;

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
public class DUsedCornerBracket extends DBaseUsedMaterial {

    private Long cornerBracketCode;
    private String ghostCode;

    public DUsedCornerBracket(Long id){
        this.setId(id);
    }

    @Override
    public void validate(){
        super.validate();
        new ValidationBuilder()
                .add(new NamedValidator<>("Código do Fantasma", new ObjetoNaoNuloValidator()), this.ghostCode)
                .add(new NamedValidator<>("Código da Cantoneira", new ObjetoNaoNuloValidator()), this.cornerBracketCode)
                .add(new NamedValidator<>("Código da Cantoneira", new NumeroMaiorQueZeroValidator()), this.cornerBracketCode)
                .validate();
    }
}
