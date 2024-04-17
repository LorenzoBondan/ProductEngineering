package br.com.todeschini.domain.business.auth.user;

import br.com.todeschini.domain.business.auth.role.DRole;
import br.com.todeschini.domain.validation.NamedValidator;
import br.com.todeschini.domain.validation.ValidationBuilder;
import br.com.todeschini.domain.validation.impl.*;
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
    private Long id;
    private String name;
    private String password;
    private String email;
    private String imgUrl;
    private List<DRole> roles = new ArrayList<>();

    public DUser(Long id){
        this.id = id;
    }

    public void validate(){
        new ValidationBuilder()
                .add(new NamedValidator<>("Nome", new ObjetoNaoNuloValidator()), this.name)
                .add(new NamedValidator<>("Nome", new NaoBrancoValidator()), this.name)
                .add(new NamedValidator<>("Nome", new TamanhoMinimoValidator(3)), this.name)
                .add(new NamedValidator<>("Nome", new TamanhoMaximoValidator(50)), this.name)
                .add(new NamedValidator<>("Senha", new ObjetoNaoNuloValidator()), this.password)
                .add(new NamedValidator<>("Senha", new NaoBrancoValidator()), this.password)
                .add(new NamedValidator<>("Senha", new TamanhoMinimoValidator(5)), this.password)
                .add(new NamedValidator<>("Senha", new TamanhoMaximoValidator(50)), this.password)
                .add(new NamedValidator<>("Email", new ObjetoNaoNuloValidator()), this.email)
                .add(new NamedValidator<>("Email", new EmailValidator()), this.email)
                .add(new NamedValidator<>("ImgUrl", new NaoBrancoValidator()), this.imgUrl)
                .add(new NamedValidator<>("ImgUrl", new TamanhoMinimoValidator(3)), this.imgUrl)
                .validate();
    }
}
