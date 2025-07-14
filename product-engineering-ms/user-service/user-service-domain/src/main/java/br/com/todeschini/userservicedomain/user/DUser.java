package br.com.todeschini.userservicedomain.user;

import br.com.todeschini.libvalidationhandler.validation.NamedValidator;
import br.com.todeschini.libvalidationhandler.validation.ValidationBuilder;
import br.com.todeschini.userservicedomain.role.DRole;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DUser {

    @EqualsAndHashCode.Include
    private Integer id;
    private String name;
    private String password;
    private String email;

    private List<DRole> roles = new ArrayList<>();

    public DUser(Integer id){
        this.id = id;
    }

    public DUser(Integer id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public void validate(){
        new ValidationBuilder()
                .add(new NamedValidator<>("Name", new NotNullValidator()), this.name)
                .add(new NamedValidator<>("Name", new NotBlankValidator()), this.name)
                .add(new NamedValidator<>("Name", new SpecialCharactersValidator()), this.name)
                .add(new NamedValidator<>("Name", new MinLengthValidator(3)), this.name)
                .add(new NamedValidator<>("Name", new MaxLengthValidator(50)), this.name)
                .add(new NamedValidator<>("Password", new NotNullValidator()), this.password)
                .add(new NamedValidator<>("Password", new NotBlankValidator()), this.password)
                .add(new NamedValidator<>("Password", new MinLengthValidator(5)), this.password)
                .add(new NamedValidator<>("Password", new MaxLengthValidator(50)), this.password)
                .add(new NamedValidator<>("Email", new NotNullValidator()), this.email)
                .add(new NamedValidator<>("Email", new EmailValidator()), this.email)
                .add(new NamedValidator<>("Roles", new NotNullValidator()), this.roles)
                .validate();
    }
}
