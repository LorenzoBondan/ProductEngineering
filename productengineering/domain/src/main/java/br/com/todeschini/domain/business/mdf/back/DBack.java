package br.com.todeschini.domain.business.mdf.back;

import br.com.todeschini.domain.business.basedomains.DBaseMaterial;
import br.com.todeschini.domain.business.mdf.usedbacksheet.DUsedBackSheet;
import br.com.todeschini.domain.validation.NamedValidator;
import br.com.todeschini.domain.validation.ValidationBuilder;
import br.com.todeschini.domain.validation.impl.LongTamanhoMinimoValidator;
import br.com.todeschini.domain.validation.impl.NumeroMaiorQueZeroValidator;
import br.com.todeschini.domain.validation.impl.ObjetoNaoNuloValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DBack extends DBaseMaterial {

    private Integer suffix;
    private Double thickness;
    private Integer measure1;
    private Integer measure2;

    private List<DUsedBackSheet> usedBackSheets = new ArrayList<>();

    public DBack(Long code){
        this.setCode(code);
    }

    @Override
    public void validate(){
        super.validate();
        new ValidationBuilder()
                .add(new NamedValidator<>("Código", new LongTamanhoMinimoValidator(8)), this.getCode())
                .add(new NamedValidator<>("Sufixo", new ObjetoNaoNuloValidator()), this.suffix)
                .add(new NamedValidator<>("Sufixo", new NumeroMaiorQueZeroValidator()), this.suffix)
                .add(new NamedValidator<>("Espessura", new ObjetoNaoNuloValidator()), this.thickness)
                .add(new NamedValidator<>("Espessura", new NumeroMaiorQueZeroValidator()), this.thickness)
                .add(new NamedValidator<>("Medida 1", new ObjetoNaoNuloValidator()), this.measure1)
                .add(new NamedValidator<>("Medida 1", new NumeroMaiorQueZeroValidator()), this.measure1)
                .add(new NamedValidator<>("Medida 2", new ObjetoNaoNuloValidator()), this.measure2)
                .add(new NamedValidator<>("Medida 2", new NumeroMaiorQueZeroValidator()), this.measure2)
                .validate();
    }
}
