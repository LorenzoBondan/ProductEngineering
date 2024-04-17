package br.com.todeschini.persistence.entities.mdf;

import br.com.todeschini.persistence.entities.baseentities.BaseMaterial;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_painting_border_background")
public class PaintingBorderBackground extends BaseMaterial {

    @OneToMany(mappedBy = "paintingBorderBackground")
    private List<UsedPaintingBorderBackground> usedPaintingBorderBackgrounds = new ArrayList<>();
}
