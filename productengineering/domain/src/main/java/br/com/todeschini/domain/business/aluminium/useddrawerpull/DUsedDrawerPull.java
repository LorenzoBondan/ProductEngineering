package br.com.todeschini.domain.business.aluminium.useddrawerpull;

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
public class DUsedDrawerPull extends DBaseUsedAluminiumMaterial {

    private Long drawerPullCode;

    public DUsedDrawerPull(Long id){
        this.setId(id);
    }

    @Override
    public void validate(){
        super.validate();
        new ValidationBuilder()
                .add(new NamedValidator<>("Código do Puxador", new ObjetoNaoNuloValidator()), this.drawerPullCode)
                .add(new NamedValidator<>("Código do Puxador", new NumeroMaiorQueZeroValidator()), this.drawerPullCode)
                .validate();
    }
}
