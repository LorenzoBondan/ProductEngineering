package br.com.todeschini.domain.business.packaging.nonwovenfabric;

import br.com.todeschini.domain.business.basedomains.DBaseMaterial;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DNonwovenFabric extends DBaseMaterial {

    public DNonwovenFabric(Long code){
        this.setCode(code);
    }

    @Override
    public void validate(){
        super.validate();
    }
}
