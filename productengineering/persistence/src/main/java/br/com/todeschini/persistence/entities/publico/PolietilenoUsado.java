package br.com.todeschini.persistence.entities.publico;

import br.com.todeschini.domain.metadata.Entidade;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
@DiscriminatorValue("PolietilenoUsado")
@Entidade
public class PolietilenoUsado extends MaterialUsado {

    public PolietilenoUsado(){
        this.setUnidadeMedida("M");
    }
}
