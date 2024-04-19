package br.com.todeschini.persistence.entities.mdf;

import br.com.todeschini.persistence.entities.baseentities.BaseMaterial;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
@Table(name = "tb_painting")
public class Painting extends BaseMaterial {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "painting_type_id")
    private PaintingType paintingType;

    @OneToMany(mappedBy = "painting")
    private List<UsedPainting> usedPaintings = new ArrayList<>();
}
