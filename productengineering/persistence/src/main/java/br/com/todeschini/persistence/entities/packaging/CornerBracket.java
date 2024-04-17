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
@Table(name = "tb_corner_bracket")
public class CornerBracket extends BaseMaterial {

    @OneToMany(mappedBy = "cornerBracket")
    private List<UsedCornerBracket> usedCornerBrackets = new ArrayList<>();
}
