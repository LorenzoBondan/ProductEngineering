package br.com.todeschini.domain.business.mdf.paintingborderbackground;

import br.com.todeschini.domain.business.basedomains.DBaseMaterial;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DPaintingBorderBackground extends DBaseMaterial {

    public DPaintingBorderBackground(Long code){
        this.setCode(code);
    }

    @Override
    public void validate(){
        super.validate();
    }
}
