package br.com.todeschini.domain.business.publico.color;

import br.com.todeschini.domain.business.enums.DStatus;
import br.com.todeschini.domain.validation.NamedValidator;
import br.com.todeschini.domain.validation.ValidationBuilder;
import br.com.todeschini.domain.validation.impl.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DColor {

    @EqualsAndHashCode.Include
    private Long code;
    private String name;
    private DStatus status;

    public DColor(Long code){
        this.code = code;
    }

    public void validate(){
        new ValidationBuilder()
                .add(new NamedValidator<>("Código", new ObjetoNaoNuloValidator()), this.code)
                .add(new NamedValidator<>("Código", new NumeroMaiorQueZeroValidator()), this.code)
                .add(new NamedValidator<>("Código", new LongTamanhoMinimoValidator(3)), this.code)
                .add(new NamedValidator<>("Código", new LongTamanhoMaximoValidator(9)), this.code)
                .add(new NamedValidator<>("Name", new ObjetoNaoNuloValidator()), this.name)
                .add(new NamedValidator<>("Name", new NaoBrancoValidator()), this.name)
                .add(new NamedValidator<>("Name", new CaracteresEspeciaisValidator()), this.name)
                .add(new NamedValidator<>("Name", new TamanhoMinimoValidator(3)), this.name)
                .add(new NamedValidator<>("Name", new TamanhoMaximoValidator(30)), this.name)
                .validate();
    }
}
