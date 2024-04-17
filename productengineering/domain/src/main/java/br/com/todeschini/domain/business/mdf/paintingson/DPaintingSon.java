package br.com.todeschini.domain.business.mdf.paintingson;

import br.com.todeschini.domain.business.mdf.back.DBack;
import br.com.todeschini.domain.business.mdf.usedpainting.DUsedPainting;
import br.com.todeschini.domain.business.mdf.usedpaintingborderbackground.DUsedPaintingBorderBackground;
import br.com.todeschini.domain.business.mdf.usedpolyester.DUsedPolyester;
import br.com.todeschini.domain.business.publico.son.DSon;
import br.com.todeschini.domain.validation.NamedValidator;
import br.com.todeschini.domain.validation.ValidationBuilder;
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
public class DPaintingSon extends DSon {

    private Boolean satin;
    private Boolean highShine;
    private Boolean satinGlass;
    private Integer faces;
    private Boolean special;
    private Integer suffix;

    private DBack back;
    private List<DUsedPainting> paintings = new ArrayList<>();
    private List<DUsedPaintingBorderBackground> paintingBorderBackgrounds = new ArrayList<>();
    private List<DUsedPolyester> polyesters = new ArrayList<>();

    public DPaintingSon(Long code){
        this.setCode(code);
    }

    @Override
    public void validate(){
        super.validate();
        new ValidationBuilder()
                .add(new NamedValidator<>("Acetinada", new ObjetoNaoNuloValidator()), this.satin)
                .add(new NamedValidator<>("Alto Brilho", new ObjetoNaoNuloValidator()), this.highShine)
                .add(new NamedValidator<>("Acetinada Vidro", new ObjetoNaoNuloValidator()), this.satinGlass)
                .add(new NamedValidator<>("Faces", new ObjetoNaoNuloValidator()), this.faces)
                .add(new NamedValidator<>("Faces", new NumeroMaiorQueZeroValidator()), this.faces)
                .add(new NamedValidator<>("Especial", new ObjetoNaoNuloValidator()), this.special)
                .validate();
    }
}
