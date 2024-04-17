package br.com.todeschini.persistence.entities.publico;

import br.com.todeschini.persistence.entities.packaging.Ghost;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@DiscriminatorValue("Father")
public class Father extends Item implements Serializable {

    @OneToMany(mappedBy = "father", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Son> sons = new ArrayList<>();

    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tb_father_attachment",
            joinColumns = @JoinColumn(name = "father_id"),
            inverseJoinColumns = @JoinColumn(name = "attachment_id"))
    private Set<Attachment> attachments = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "ghost_id")
    private Ghost ghost;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;

    public Father(Long code){
        this.setCode(code);
    }

    public Father(Long code, String description, Integer measure1, Integer measure2, Integer measure3, Ghost ghost) {
        super(code, description, measure1, measure2, measure3);
        this.ghost = ghost;
    }
}
