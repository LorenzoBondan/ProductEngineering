package br.com.todeschini.domain.business.mdf.painting;

import br.com.todeschini.domain.business.basedomains.DBaseMaterial;
import br.com.todeschini.domain.business.mdf.paintingtype.DPaintingType;
import br.com.todeschini.domain.validation.NamedValidator;
import br.com.todeschini.domain.validation.ValidationBuilder;
import br.com.todeschini.domain.validation.impl.ObjetoNaoNuloValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DPainting extends DBaseMaterial {

    private DPaintingType paintingType;

    public DPainting(Long code){
        this.setCode(code);
    }

    @Override
    public void validate(){
        super.validate();
        new ValidationBuilder()
                .add(new NamedValidator<>("Tipo de Pintura", new ObjetoNaoNuloValidator()), this.paintingType)
                .validate();
    }
}
