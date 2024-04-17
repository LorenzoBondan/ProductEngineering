package br.com.todeschini.persistence.entities.baseentities;

import br.com.todeschini.persistence.entities.AuditInfo;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
@MappedSuperclass
public abstract class BaseUsedMaterial extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double netQuantity;
    private Double grossQuantity;
    private String measurementUnit;

    public abstract Double calculateNetQuantity();
    public abstract void calculateGrossQuantity();
}
