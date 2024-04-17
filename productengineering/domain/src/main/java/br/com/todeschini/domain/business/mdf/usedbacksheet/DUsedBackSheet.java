package br.com.todeschini.domain.business.mdf.usedbacksheet;

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
public class DUsedBackSheet extends DBaseUsedMaterial {

    private Long sheetCode;
    private Long backCode;

    public DUsedBackSheet(Long id){
        this.setId(id);
    }

    @Override
    public void validate(){
        super.validate();
        new ValidationBuilder()
                .add(new NamedValidator<>("Código da Chapa", new ObjetoNaoNuloValidator()), this.sheetCode)
                .add(new NamedValidator<>("Código da Chapa", new NumeroMaiorQueZeroValidator()), this.sheetCode)
                .add(new NamedValidator<>("Código do Fundo", new ObjetoNaoNuloValidator()), this.backCode)
                .add(new NamedValidator<>("Código do Fundo", new NumeroMaiorQueZeroValidator()), this.backCode)
                .validate();
    }
}
