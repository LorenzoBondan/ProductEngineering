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
public class Chapa extends Material {

    private Integer espessura;
    private Integer faces;
}
