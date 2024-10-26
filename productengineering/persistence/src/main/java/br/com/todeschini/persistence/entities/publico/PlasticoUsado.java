package br.com.todeschini.persistence.entities.publico;

import br.com.todeschini.domain.metadata.Entidade;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
@DiscriminatorValue("PlasticoUsado")
@Entidade
public class PlasticoUsado extends MaterialUsado {

    public PlasticoUsado(){
        this.setUnidadeMedida("M²");
    }
}
