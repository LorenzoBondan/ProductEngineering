package br.com.todeschini.domain.business.publico.father;

import br.com.todeschini.domain.business.packaging.ghost.DGhost;
import br.com.todeschini.domain.business.publico.attachment.DAttachment;
import br.com.todeschini.domain.business.publico.color.DColor;
import br.com.todeschini.domain.business.publico.item.DItem;
import br.com.todeschini.domain.business.publico.son.DSon;
import br.com.todeschini.domain.validation.NamedValidator;
import br.com.todeschini.domain.validation.ValidationBuilder;
import br.com.todeschini.domain.validation.impl.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DFather extends DItem {

    private DColor color;
    private DGhost ghost;
    private List<DSon> sons = new ArrayList<>();
    private List<DAttachment> attachments = new ArrayList<>();

    public DFather(Long code){
        this.setCode(code);
    }

    @Override
    public void validate(){
        super.validate();
        new ValidationBuilder()
                .add(new NamedValidator<>("Cor", new ObjetoNaoNuloValidator()), this.color)
                .validate();
    }
}
