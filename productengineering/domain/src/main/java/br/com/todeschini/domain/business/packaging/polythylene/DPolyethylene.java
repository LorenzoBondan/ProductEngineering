package br.com.todeschini.domain.business.packaging.polythylene;

import br.com.todeschini.domain.business.basedomains.DBaseMaterial;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DPolyethylene extends DBaseMaterial {

    public DPolyethylene(Long code){
        this.setCode(code);
    }

    @Override
    public void validate(){
        super.validate();
    }
}
