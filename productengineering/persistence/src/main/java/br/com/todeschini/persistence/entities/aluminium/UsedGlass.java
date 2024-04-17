package br.com.todeschini.persistence.entities.aluminium;

import br.com.todeschini.persistence.entities.baseentities.BaseUsedAluminiumMaterial;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_used_glass")
public class UsedGlass extends BaseUsedAluminiumMaterial {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "glass_id")
    private Glass glass;

    public UsedGlass(){
        this.setMeasurementUnit("UN");
    }

    @Override
    public Double calculateQuantity() {
        this.setQuantity(1.0);
        return 1.0;
    }
}
