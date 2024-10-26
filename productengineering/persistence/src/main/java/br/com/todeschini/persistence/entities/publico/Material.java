package br.com.todeschini.persistence.entities.publico;

import br.com.todeschini.domain.metadata.Entidade;
import br.com.todeschini.persistence.entities.enums.TipoMaterial;
import br.com.todeschini.persistence.entities.enums.converters.TipoMaterialConverter;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "cdmaterial", callSuper = false)
@Entity
@Table(name = "tb_material")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
@Entidade
public class Material extends AuditoriaInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cdmaterial;
    private String descricao;

    @Convert(converter = TipoMaterialConverter.class)
    private TipoMaterial tipoMaterial;

    private LocalDate implantacao;
    private Double porcentagemPerda;
    private Double valor;

    @ManyToOne
    @JoinColumn(name = "cdcor")
    private Cor cor;

    @OneToMany(mappedBy = "material", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<MaterialUsado> materiaisUsados = new ArrayList<>();

    public Material(Integer cdmaterial) {
        this.cdmaterial = cdmaterial;
    }
}
