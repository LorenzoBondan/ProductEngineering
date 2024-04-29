package br.com.todeschini.persistence.entities.mdf;

import br.com.todeschini.persistence.entities.audit.AuditInfo;
import javax.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(name = "tb_painting_type")
public class PaintingType extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    @OneToMany(mappedBy = "paintingType")
    private List<Painting> paintings = new ArrayList<>();
}
