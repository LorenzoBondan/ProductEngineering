package br.com.todeschini.domain.business.publico.son;

import br.com.todeschini.domain.business.guides.guide.DGuide;
import br.com.todeschini.domain.business.publico.color.DColor;
import br.com.todeschini.domain.business.publico.item.DItem;
import br.com.todeschini.domain.validation.NamedValidator;
import br.com.todeschini.domain.validation.ValidationBuilder;
import br.com.todeschini.domain.validation.impl.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DSon extends DItem {

    private Long fatherCode;
    private DColor color;
    private DGuide guide;

    public DSon(Long code){
        this.setCode(code);
    }

    @Override
    public void validate(){
        super.validate();
        new ValidationBuilder()
                .add(new NamedValidator<>("Código", new LongTamanhoMinimoValidator(9)), this.getCode())
                .add(new NamedValidator<>("Cor", new ObjetoNaoNuloValidator()), this.color)
                .add(new NamedValidator<>("Código Pai", new ObjetoNaoNuloValidator()), this.fatherCode)
                .add(new NamedValidator<>("Código Pai", new NumeroMaiorQueZeroValidator()), this.fatherCode)
                .validate();
    }
}
