package br.com.todeschini.domain.business.mdf.usedpolyester;

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
public class DUsedPolyester extends DBaseUsedMaterial {

    private Long polyesterCode;
    private Long paintingSonCode;

    public DUsedPolyester(Long id){
        this.setId(id);
    }

    @Override
    public void validate(){
        super.validate();
        new ValidationBuilder()
                .add(new NamedValidator<>("Código do Filho", new ObjetoNaoNuloValidator()), this.paintingSonCode)
                .add(new NamedValidator<>("Código do Filho", new NumeroMaiorQueZeroValidator()), this.paintingSonCode)
                .add(new NamedValidator<>("Código do Poliéster", new ObjetoNaoNuloValidator()), this.polyesterCode)
                .add(new NamedValidator<>("Código do Poliéster", new NumeroMaiorQueZeroValidator()), this.polyesterCode)
                .validate();
    }
}
