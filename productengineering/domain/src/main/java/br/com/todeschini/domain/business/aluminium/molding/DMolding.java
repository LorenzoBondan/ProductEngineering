package br.com.todeschini.domain.business.aluminium.molding;

import br.com.todeschini.domain.business.publico.attachment.DAttachment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DMolding extends DAttachment {

    public DMolding(Long code){
        this.setCode(code);
    }

    @Override
    public void validate(){
        super.validate();
    }
}
