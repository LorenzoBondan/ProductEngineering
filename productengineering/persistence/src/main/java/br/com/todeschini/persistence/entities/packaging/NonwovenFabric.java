package br.com.todeschini.persistence.entities.packaging;

import br.com.todeschini.persistence.entities.baseentities.BaseMaterial;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_nonwoven_fabric")
public class NonwovenFabric extends BaseMaterial {

    @OneToMany(mappedBy = "nonwovenFabric")
    private List<UsedNonwovenFabric> usedNonwovenFabrics = new ArrayList<>();
}
