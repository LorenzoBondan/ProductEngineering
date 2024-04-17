package br.com.todeschini.domain.business.mdf.usedpainting;

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
public class DUsedPainting extends DBaseUsedMaterial {

    private Long paintingCode;
    private Long paintingSonCode;

    public DUsedPainting(Long id){
        this.setId(id);
    }

    @Override
    public void validate(){
        super.validate();
        new ValidationBuilder()
                .add(new NamedValidator<>("Código do Filho", new ObjetoNaoNuloValidator()), this.paintingSonCode)
                .add(new NamedValidator<>("Código do Filho", new NumeroMaiorQueZeroValidator()), this.paintingSonCode)
                .add(new NamedValidator<>("Código da Pintura", new ObjetoNaoNuloValidator()), this.paintingCode)
                .add(new NamedValidator<>("Código da Pintura", new NumeroMaiorQueZeroValidator()), this.paintingCode)
                .validate();
    }
}
