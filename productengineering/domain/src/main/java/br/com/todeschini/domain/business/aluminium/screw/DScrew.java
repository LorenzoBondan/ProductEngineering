package br.com.todeschini.domain.business.aluminium.screw;

import br.com.todeschini.domain.business.publico.attachment.DAttachment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DScrew extends DAttachment {

    public DScrew(Long code){
        this.setCode(code);
    }

    @Override
    public void validate(){
        super.validate();
    }
}
