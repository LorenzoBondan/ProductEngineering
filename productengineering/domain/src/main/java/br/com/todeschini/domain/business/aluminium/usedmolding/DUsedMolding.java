package br.com.todeschini.domain.business.aluminium.usedmolding;

import br.com.todeschini.domain.business.basedomains.DBaseUsedAluminiumMaterial;
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
public class DUsedMolding extends DBaseUsedAluminiumMaterial {

    private Long moldingCode;

    public DUsedMolding(Long id){
        this.setId(id);
    }

    @Override
    public void validate(){
        super.validate();
        new ValidationBuilder()
                .add(new NamedValidator<>("Código do Perfil", new ObjetoNaoNuloValidator()), this.moldingCode)
                .add(new NamedValidator<>("Código do Perfil", new NumeroMaiorQueZeroValidator()), this.moldingCode)
                .validate();
    }
}
