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
@Table(name = "tb_used_try_square")
public class UsedTrySquare extends BaseUsedAluminiumMaterial {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "try_square_id")
    private TrySquare trySquare;

    public UsedTrySquare(){
        this.setMeasurementUnit("UN");
    }

    @Override
    public Double calculateQuantity() {
        this.setQuantity(getQuantity());
        return Double.parseDouble(String.valueOf(this.getQuantity()));
    }

    @Override
    public Double calculateValue() {
        return Math.round(trySquare.getValue() * this.getQuantity() * 1e2) / 1e2;
    }
}
