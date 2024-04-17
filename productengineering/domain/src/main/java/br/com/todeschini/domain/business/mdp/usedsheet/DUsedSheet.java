package br.com.todeschini.domain.business.mdp.usedsheet;

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
public class DUsedSheet extends DBaseUsedMaterial {

    private Long sheetCode;
    private Long mdpSonCode;

    public DUsedSheet(Long id){
        this.setId(id);
    }

    @Override
    public void validate(){
        super.validate();
        new ValidationBuilder()
                .add(new NamedValidator<>("Código do Filho", new ObjetoNaoNuloValidator()), this.mdpSonCode)
                .add(new NamedValidator<>("Código do Filho", new NumeroMaiorQueZeroValidator()), this.mdpSonCode)
                .add(new NamedValidator<>("Código da Chapa", new ObjetoNaoNuloValidator()), this.sheetCode)
                .add(new NamedValidator<>("Código da Chapa", new NumeroMaiorQueZeroValidator()), this.sheetCode)
                .validate();
    }
}
