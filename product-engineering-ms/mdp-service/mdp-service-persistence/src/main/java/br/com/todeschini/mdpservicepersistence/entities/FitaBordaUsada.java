package br.com.todeschini.mdpservicepersistence.entities;

import br.com.todeschini.itemservicepersistence.entities.MaterialUsado;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("FitaBordaUsada")
public class FitaBordaUsada extends MaterialUsado {

    public FitaBordaUsada(){
        this.setUnidadeMedida("M");
    }
}
