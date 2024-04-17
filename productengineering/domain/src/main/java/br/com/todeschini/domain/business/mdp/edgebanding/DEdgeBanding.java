package br.com.todeschini.domain.business.mdp.edgebanding;

import br.com.todeschini.domain.business.basedomains.DBaseMaterial;
import br.com.todeschini.domain.validation.NamedValidator;
import br.com.todeschini.domain.validation.ValidationBuilder;
import br.com.todeschini.domain.validation.impl.NumeroMaiorQueZeroValidator;
import br.com.todeschini.domain.validation.impl.ObjetoNaoNuloValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DEdgeBanding extends DBaseMaterial {

    private Integer height;
    private Integer thickness;

    public DEdgeBanding(Long code){
        this.setCode(code);
    }

    @Override
    public void validate(){
        super.validate();
        new ValidationBuilder()
                .add(new NamedValidator<>("Altura", new ObjetoNaoNuloValidator()), this.height)
                .add(new NamedValidator<>("Altura", new NumeroMaiorQueZeroValidator()), this.height)
                .add(new NamedValidator<>("Espessura", new ObjetoNaoNuloValidator()), this.thickness)
                .add(new NamedValidator<>("Espessura", new NumeroMaiorQueZeroValidator()), this.thickness)
                .validate();
    }
}
