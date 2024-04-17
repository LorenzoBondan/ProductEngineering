package br.com.todeschini.domain.business.publico.item;

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
public class DItem {

    @EqualsAndHashCode.Include
    private Long code;
    private String description;
    private Integer measure1;
    private Integer measure2;
    private Integer measure3;
    private String measurementUnit;

    public DItem(Long code){
        this.code = code;
    }

    public void validate(){
        new ValidationBuilder()
                .add(new NamedValidator<>("Código", new ObjetoNaoNuloValidator()), this.code)
                .add(new NamedValidator<>("Código", new NumeroMaiorQueZeroValidator()), this.code)
                .add(new NamedValidator<>("Descrição", new ObjetoNaoNuloValidator()), this.description)
                .add(new NamedValidator<>("Descrição", new NaoBrancoValidator()), this.description)
                .add(new NamedValidator<>("Descrição", new TamanhoMinimoValidator(3)), this.description)
                .add(new NamedValidator<>("Descrição", new TamanhoMaximoValidator(100)), this.description)
                .add(new NamedValidator<>("Medida 1", new NumeroMaiorQueZeroValidator()), this.measure1)
                .add(new NamedValidator<>("Medida 2", new NumeroMaiorQueZeroValidator()), this.measure2)
                .add(new NamedValidator<>("Medida 3", new NumeroMaiorQueZeroValidator()), this.measure3)
                .validate();
    }
}
