package br.com.todeschini.persistence.entities.publico;

import br.com.todeschini.domain.metadata.Entidade;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
@DiscriminatorValue("CantoneiraUsada")
@Entidade
public class CantoneiraUsada extends MaterialUsado {

    public CantoneiraUsada(){
        this.setUnidadeMedida("UN");
    }
}
