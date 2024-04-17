package br.com.todeschini.domain.business.mdp.usededgebanding;

import br.com.todeschini.domain.business.basedomains.DBaseUsedMaterial;
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
public class DUsedEdgeBanding extends DBaseUsedMaterial {

    private Long edgeBandingCode;
    private Long mdpSonCode;

    public DUsedEdgeBanding(Long id){
        this.setId(id);
    }

    @Override
    public void validate(){
        super.validate();
        new ValidationBuilder()
                .add(new NamedValidator<>("Código do Filho", new ObjetoNaoNuloValidator()), this.mdpSonCode)
                .add(new NamedValidator<>("Código do Filho", new NumeroMaiorQueZeroValidator()), this.mdpSonCode)
                .add(new NamedValidator<>("Código da Fita Borda", new ObjetoNaoNuloValidator()), this.edgeBandingCode)
                .add(new NamedValidator<>("Código da Fita Borda", new NumeroMaiorQueZeroValidator()), this.edgeBandingCode)
                .validate();
    }
}
