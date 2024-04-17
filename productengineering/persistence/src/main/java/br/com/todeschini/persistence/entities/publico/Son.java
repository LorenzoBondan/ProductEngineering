package br.com.todeschini.persistence.entities.publico;

import br.com.todeschini.persistence.entities.guides.Guide;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Son extends Item implements Serializable {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "father_id")
    private Father father;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;

    @ManyToOne
    @JoinColumn(name = "guide_id")
    private Guide guide;

    public Son(Long code, String description, Integer measure1, Integer measure2, Integer measure3) {
        super(code, description, measure1, measure2, measure3);
    }
}
