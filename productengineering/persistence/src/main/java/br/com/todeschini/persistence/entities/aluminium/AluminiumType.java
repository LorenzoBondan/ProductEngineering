package br.com.todeschini.persistence.entities.aluminium;

import br.com.todeschini.persistence.entities.AuditInfo;
import javax.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(name = "tb_aluminium_type")
public class AluminiumType extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double lessQuantity;

    @OneToMany(mappedBy = "aluminiumType")
    private List<AluminiumSon> aluminiumSons = new ArrayList<>();
}
