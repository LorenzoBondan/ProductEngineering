package br.com.todeschini.domain.business.configurator.structs.mdpitem;

import br.com.todeschini.domain.validation.NamedValidator;
import br.com.todeschini.domain.validation.ValidationBuilder;
import br.com.todeschini.domain.validation.impl.NumeroMaiorOuIgualAZeroValidator;
import br.com.todeschini.domain.validation.impl.NumeroMaiorQueZeroValidator;
import br.com.todeschini.domain.validation.impl.ObjetoNaoNuloValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DMDPItem {

    private List<Object> list;
    private Integer edgeLength;
    private Integer edgeWidth;
    private Long glueCode;

    public void validate(){
        new ValidationBuilder()
                .add(new NamedValidator<>("Lista de filhos", new ObjetoNaoNuloValidator()), this.list)
                .add(new NamedValidator<>("Quantidade de bordas no comprimento", new ObjetoNaoNuloValidator()), this.edgeLength)
                .add(new NamedValidator<>("Quantidade de bordas no comprimento", new NumeroMaiorOuIgualAZeroValidator()), this.edgeLength)
                .add(new NamedValidator<>("Quantidade de bordas na largura", new ObjetoNaoNuloValidator()), this.edgeWidth)
                .add(new NamedValidator<>("Quantidade de bordas na largura", new NumeroMaiorOuIgualAZeroValidator()), this.edgeWidth)
                .add(new NamedValidator<>("Código da Cola", new ObjetoNaoNuloValidator()), this.glueCode)
                .add(new NamedValidator<>("Código da Cola", new NumeroMaiorQueZeroValidator()), this.glueCode)
                .validate();
    }
}
