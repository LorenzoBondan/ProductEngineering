package br.com.todeschini.domain.business.configurator.structs.mdfitem;

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
public class DMDFItem {

    private List<Object> list;
    private Boolean satin;
    private Boolean highShine;
    private Boolean satinGlass;
    private Integer faces;
    private Boolean special;
    private Long paintingBorderBackgroundId;
    private Long polyesterId;

    public void validate(){
        new ValidationBuilder()
                .add(new NamedValidator<>("Lista de filhos", new ObjetoNaoNuloValidator()), this.list)
                .add(new NamedValidator<>("Acetinada", new ObjetoNaoNuloValidator()), this.satin)
                .add(new NamedValidator<>("Acetinada Vidros", new ObjetoNaoNuloValidator()), this.satinGlass)
                .add(new NamedValidator<>("Alto Brilho", new ObjetoNaoNuloValidator()), this.highShine)
                .add(new NamedValidator<>("Faces", new ObjetoNaoNuloValidator()), this.faces)
                .add(new NamedValidator<>("Faces", new NumeroMaiorQueZeroValidator()), this.faces)
                .add(new NamedValidator<>("Especial", new ObjetoNaoNuloValidator()), this.special)
                .add(new NamedValidator<>("Pintura Borda Fundo", new ObjetoNaoNuloValidator()), this.paintingBorderBackgroundId)
                .add(new NamedValidator<>("Poliéster", new ObjetoNaoNuloValidator()), this.polyesterId)
                .validate();
    }
}
