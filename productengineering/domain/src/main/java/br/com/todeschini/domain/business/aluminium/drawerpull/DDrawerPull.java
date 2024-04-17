package br.com.todeschini.domain.business.aluminium.drawerpull;

import br.com.todeschini.domain.business.publico.attachment.DAttachment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DDrawerPull extends DAttachment {

    public DDrawerPull(Long code){
        this.setCode(code);
    }

    @Override
    public void validate(){
        super.validate();
    }
}
