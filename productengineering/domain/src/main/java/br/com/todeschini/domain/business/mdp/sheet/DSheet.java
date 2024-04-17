package br.com.todeschini.domain.business.mdp.sheet;

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
public class DSheet extends DBaseMaterial {

    private Double thickness;
    private Integer faces;
    private Long materialId;

    public DSheet(Long code){
        this.setCode(code);
    }

    @Override
    public void validate(){
        super.validate();
        new ValidationBuilder()
                .add(new NamedValidator<>("Espessura", new ObjetoNaoNuloValidator()), this.thickness)
                .add(new NamedValidator<>("Espessura", new NumeroMaiorQueZeroValidator()), this.thickness)
                .add(new NamedValidator<>("Faces", new ObjetoNaoNuloValidator()), this.faces)
                .add(new NamedValidator<>("Faces", new NumeroMaiorQueZeroValidator()), this.faces)
                .add(new NamedValidator<>("Material", new ObjetoNaoNuloValidator()), this.materialId)
                .validate();
    }
}
