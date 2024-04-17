package br.com.todeschini.domain.business.configurator.structs.aluminiumitem;

import br.com.todeschini.domain.configurator.AluminiumConfigurator;
import br.com.todeschini.domain.configurator.ScrewConfigurator;
import br.com.todeschini.domain.validation.NamedValidator;
import br.com.todeschini.domain.validation.ValidationBuilder;
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
public class DAluminiumItem {

    private List<Object> list;
    private AluminiumConfigurator config;
    private Long aluminiumTypeId;
    private Long drawerPullCode;
    private Long trySquareCode;
    private Integer trySquareQuantity;
    private List<ScrewConfigurator> screws;
    private Long moldingCode;
    private Long glueCode;

    public void validate(){
        new ValidationBuilder()
                .add(new NamedValidator<>("Lista de filhos", new ObjetoNaoNuloValidator()), this.list)
                .add(new NamedValidator<>("Configuração", new ObjetoNaoNuloValidator()), this.config)
                .add(new NamedValidator<>("Tipo de Alumínio", new ObjetoNaoNuloValidator()), this.aluminiumTypeId)
                .add(new NamedValidator<>("Código do Puxador", new ObjetoNaoNuloValidator()), this.drawerPullCode)
                .add(new NamedValidator<>("Código do Puxador", new NumeroMaiorQueZeroValidator()), this.drawerPullCode)
                .add(new NamedValidator<>("Código da Esquadreta", new ObjetoNaoNuloValidator()), this.trySquareCode)
                .add(new NamedValidator<>("Código da Esquadreta", new NumeroMaiorQueZeroValidator()), this.trySquareCode)
                .add(new NamedValidator<>("Quantidade da Esquadreta", new ObjetoNaoNuloValidator()), this.trySquareQuantity)
                .add(new NamedValidator<>("Quantidade da Esquadreta", new NumeroMaiorQueZeroValidator()), this.trySquareQuantity)
                .add(new NamedValidator<>("Código do Perfil", new ObjetoNaoNuloValidator()), this.moldingCode)
                .add(new NamedValidator<>("Código do Perfil", new NumeroMaiorQueZeroValidator()), this.moldingCode)
                .add(new NamedValidator<>("Código da Cola", new ObjetoNaoNuloValidator()), this.glueCode)
                .add(new NamedValidator<>("Código da Cola", new NumeroMaiorQueZeroValidator()), this.glueCode)
                .validate();
    }
}
