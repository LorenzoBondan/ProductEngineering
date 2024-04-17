package br.com.todeschini.persistence.entities.baseentities;

import br.com.todeschini.persistence.entities.AuditInfo;
import br.com.todeschini.persistence.entities.aluminium.AluminiumSon;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
@MappedSuperclass
public abstract class BaseUsedAluminiumMaterial extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double quantity;
    private String measurementUnit;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "aluminium_son_id")
    private AluminiumSon aluminiumSon;

    public abstract Double calculateQuantity();
}
