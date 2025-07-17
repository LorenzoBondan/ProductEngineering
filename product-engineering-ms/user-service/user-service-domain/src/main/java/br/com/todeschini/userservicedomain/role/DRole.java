package br.com.todeschini.userservicedomain.role;

import br.com.todeschini.libvalidationhandler.validation.NamedValidator;
import br.com.todeschini.libvalidationhandler.validation.ValidationBuilder;
import br.com.todeschini.libvalidationhandler.validation.impl.TamanhoMaximoValidator;
import br.com.todeschini.libvalidationhandler.validation.impl.TamanhoMinimoValidator;
import br.com.todeschini.libvalidationhandler.validation.impl.NaoBrancoValidator;
import br.com.todeschini.libvalidationhandler.validation.impl.ObjetoNaoNuloValidator;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DRole {

    @EqualsAndHashCode.Include
    private Integer id;
    private String authority;

    public DRole(Integer id){
        this.id = id;
    };

    public void validate(){
        new ValidationBuilder()
                .add(new NamedValidator<>("Authority", new ObjetoNaoNuloValidator()), this.authority)
                .add(new NamedValidator<>("Authority", new NaoBrancoValidator()), this.authority)
                .add(new NamedValidator<>("Authority", new TamanhoMinimoValidator(8)), this.authority)
                .add(new NamedValidator<>("Authority", new TamanhoMaximoValidator(30)), this.authority)
                .validate();
    }
}
