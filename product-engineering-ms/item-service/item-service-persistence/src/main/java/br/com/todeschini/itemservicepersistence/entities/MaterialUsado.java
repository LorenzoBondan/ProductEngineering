package br.com.todeschini.itemservicepersistence.entities;

import br.com.todeschini.libauditpersistence.entities.AuditoriaInfo;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "cdmaterialUsado", callSuper = false)
@Entity
@Table(name = "tb_material_usado")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
public class MaterialUsado extends AuditoriaInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cdmaterialUsado;

    @ManyToOne
    @JoinColumn(name = "cdfilho")
    private Filho filho;

    @ManyToOne
    @JoinColumn(name = "cdmaterial")
    private Material material;

    private Double valor;
    private Double quantidadeLiquida;
    private Double quantidadeBruta;
    private String unidadeMedida;

    public MaterialUsado(Integer cdmaterialUsado) {
        this.cdmaterialUsado = cdmaterialUsado;
    }
}
