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
@Table(name = "tb_used_drawer_pull")
public class UsedDrawerPull extends BaseUsedAluminiumMaterial {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "drawer_pull_id")
    private DrawerPull drawerPull;

    public UsedDrawerPull(){
        this.setMeasurementUnit("UN");
    }

    @Override
    public Double calculateQuantity() {
        this.setQuantity(1.0);
        return 1.0;
    }

    @Override
    public Double calculateValue() {
        return Math.round(drawerPull.getValue() * this.getQuantity() * 1e2) / 1e2;
    }
}
