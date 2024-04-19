package br.com.todeschini.domain.business.auth.role;

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
public class DRole {

    @EqualsAndHashCode.Include
    private Long id;
    private String authority;

    public DRole(Long id){
        this.id = id;
    };

    public void validate(){
        new ValidationBuilder()
                .add(new NamedValidator<>("Papel", new ObjetoNaoNuloValidator()), this.authority)
                .add(new NamedValidator<>("Papel", new NaoBrancoValidator()), this.authority)
                .add(new NamedValidator<>("Papel", new TamanhoMinimoValidator(8)), this.authority)
                .add(new NamedValidator<>("Papel", new TamanhoMaximoValidator(30)), this.authority)
                .validate();
    }
}
