package br.com.todeschini.mdpservicepersistence.entities;

import br.com.todeschini.itemservicepersistence.entities.MaterialUsado;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("ColaUsada")
public class ColaUsada extends MaterialUsado {

    public ColaUsada(){
        this.setUnidadeMedida("KG");
    }
}
