package br.com.todeschini.domain.business.publico.material;

import br.com.todeschini.domain.validation.NamedValidator;
import br.com.todeschini.domain.validation.ValidationBuilder;
import br.com.todeschini.domain.validation.impl.NaoBrancoValidator;
import br.com.todeschini.domain.validation.impl.ObjetoNaoNuloValidator;
import br.com.todeschini.domain.validation.impl.TamanhoMaximoValidator;
import br.com.todeschini.domain.validation.impl.TamanhoMinimoValidator;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DMaterial {

    @EqualsAndHashCode.Include
    private Long id;
    private String name;

    public DMaterial(Long id){
        this.id = id;
    }

    public void validate(){
        new ValidationBuilder()
                .add(new NamedValidator<>("Name", new ObjetoNaoNuloValidator()), this.name)
                .add(new NamedValidator<>("Name", new NaoBrancoValidator()), this.name)
                .add(new NamedValidator<>("Name", new TamanhoMinimoValidator(3)), this.name)
                .add(new NamedValidator<>("Name", new TamanhoMaximoValidator(30)), this.name)
                .validate();
    }
}
