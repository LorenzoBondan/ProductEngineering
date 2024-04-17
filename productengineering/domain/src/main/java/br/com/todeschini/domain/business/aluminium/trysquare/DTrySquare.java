package br.com.todeschini.domain.business.aluminium.trysquare;

import br.com.todeschini.domain.business.publico.attachment.DAttachment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DTrySquare extends DAttachment {

    public DTrySquare(Long code){
        this.setCode(code);
    }

    @Override
    public void validate(){
        super.validate();
    }
}
