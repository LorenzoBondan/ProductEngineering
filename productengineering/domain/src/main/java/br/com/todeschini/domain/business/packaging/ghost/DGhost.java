package br.com.todeschini.domain.business.packaging.ghost;

import br.com.todeschini.domain.business.enums.DStatus;
import br.com.todeschini.domain.business.packaging.usedcornerbracket.DUsedCornerBracket;
import br.com.todeschini.domain.business.packaging.usednonwovenfabric.DUsedNonwovenFabric;
import br.com.todeschini.domain.business.packaging.usedplastic.DUsedPlastic;
import br.com.todeschini.domain.business.packaging.usedpolyethylene.DUsedPolyethylene;
import br.com.todeschini.domain.exceptions.ValidationException;
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
public class DGhost {

    @EqualsAndHashCode.Include
    private String code;
    private String suffix;
    private String description;
    private Integer measure1;
    private Integer measure2;
    private Integer measure3;
    private Double value;
    private DStatus status;

    private List<DUsedCornerBracket> cornerBrackets = new ArrayList<>();
    private List<DUsedNonwovenFabric> nonwovenFabrics = new ArrayList<>();
    private List<DUsedPlastic> plastics = new ArrayList<>();
    private List<DUsedPolyethylene> polyethylenes = new ArrayList<>();

    public DGhost(String code) {
        this.code = code;
    }

    public void validate() throws ValidationException {
        new ValidationBuilder()
                .add(new NamedValidator<>("Código", new ObjetoNaoNuloValidator()), this.code)
                .add(new NamedValidator<>("Código", new NaoBrancoValidator()), this.code)
                .add(new NamedValidator<>("Código", new CaracteresEspeciaisValidator()), this.code)
                .add(new NamedValidator<>("Código", new TamanhoMinimoValidator(9)), this.code)
                .add(new NamedValidator<>("Código", new TamanhoMaximoValidator(12)), this.code)
                .add(new NamedValidator<>("Descrição", new ObjetoNaoNuloValidator()), this.description)
                .add(new NamedValidator<>("Descrição", new NaoBrancoValidator()), this.description)
                .add(new NamedValidator<>("Descrição", new CaracteresEspeciaisValidator()), this.description)
                .add(new NamedValidator<>("Descrição", new TamanhoMinimoValidator(3)), this.description)
                .add(new NamedValidator<>("Descrição", new TamanhoMaximoValidator(100)), this.description)
                .add(new NamedValidator<>("Medida 1", new NumeroMaiorQueZeroValidator()), this.measure1)
                .add(new NamedValidator<>("Medida 2", new NumeroMaiorQueZeroValidator()), this.measure2)
                .add(new NamedValidator<>("Medida 3", new NumeroMaiorQueZeroValidator()), this.measure3)
                .validate();
    }
}
