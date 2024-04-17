package br.com.todeschini.domain.business.packaging.cornerbracket;

import br.com.todeschini.domain.business.basedomains.DBaseMaterial;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DCornerBracket extends DBaseMaterial {

    public DCornerBracket(Long code){
        this.setCode(code);
    }

    @Override
    public void validate(){
        super.validate();
    }
}
