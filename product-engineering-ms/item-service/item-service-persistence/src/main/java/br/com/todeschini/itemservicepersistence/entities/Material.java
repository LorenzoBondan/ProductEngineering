package br.com.todeschini.itemservicepersistence.entities;

import br.com.todeschini.itemservicepersistence.entities.enums.TipoMaterialEnum;
import br.com.todeschini.itemservicepersistence.entities.enums.converters.TipoMaterialEnumConverter;
import br.com.todeschini.libauditpersistence.entities.AuditoriaInfo;
import jakarta.persistence.*;
import lombok.*;

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
public class Material extends AuditoriaInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cdmaterial;
    private String descricao;

    @Convert(converter = TipoMaterialEnumConverter.class)
    private TipoMaterialEnum tipoMaterial;

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
