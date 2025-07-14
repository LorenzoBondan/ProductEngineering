package br.com.todeschini.userservicedomain.role;

import br.com.todeschini.libvalidationhandler.validation.NamedValidator;
import br.com.todeschini.libvalidationhandler.validation.ValidationBuilder;
import br.com.todeschini.libvalidationhandler.validation.impl.MaxLengthValidator;
import br.com.todeschini.libvalidationhandler.validation.impl.MinLengthValidator;
import br.com.todeschini.libvalidationhandler.validation.impl.NotBlankValidator;
import br.com.todeschini.libvalidationhandler.validation.impl.NotNullValidator;
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
                .add(new NamedValidator<>("Authority", new NotNullValidator()), this.authority)
                .add(new NamedValidator<>("Authority", new NotBlankValidator()), this.authority)
                .add(new NamedValidator<>("Authority", new MinLengthValidator(8)), this.authority)
                .add(new NamedValidator<>("Authority", new MaxLengthValidator(30)), this.authority)
                .validate();
    }
}
