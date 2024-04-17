package br.com.todeschini.domain.business.mdf.polyester;

import br.com.todeschini.domain.business.basedomains.DBaseMaterial;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DPolyester extends DBaseMaterial {

    public DPolyester(Long code){
        this.setCode(code);
    }

    @Override
    public void validate(){
        super.validate();
    }
}
