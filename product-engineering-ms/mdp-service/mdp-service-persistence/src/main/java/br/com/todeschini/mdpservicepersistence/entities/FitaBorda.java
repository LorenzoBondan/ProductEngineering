package br.com.todeschini.mdpservicepersistence.entities;

import br.com.todeschini.itemservicepersistence.entities.Material;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class FitaBorda extends Material {

    private Integer altura;
    private Integer espessura;
}
