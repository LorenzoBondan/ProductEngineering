package br.com.todeschini.persistence.entities.publico;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Attachment extends Item {

    @ManyToMany(mappedBy = "attachments")
    private Set<Father> fathers = new HashSet<>();

    public Attachment(Long code, String description, Integer measure1, Integer measure2, Integer measure3) {
        super(code, description, measure1, measure2, measure3);
    }
}
