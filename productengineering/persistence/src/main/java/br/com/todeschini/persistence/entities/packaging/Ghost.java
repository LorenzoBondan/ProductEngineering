package br.com.todeschini.persistence.entities.packaging;

import br.com.todeschini.persistence.entities.audit.AuditInfo;
import br.com.todeschini.persistence.entities.publico.Father;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "code", callSuper = false)
@Entity
@Table(name = "tb_ghost")
public class Ghost extends AuditInfo {

    @Id
    private String code;
    private String suffix;
    private String description;
    private Integer measure1;
    private Integer measure2;
    private Integer measure3;

    @OneToMany(mappedBy = "ghost", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private Set<UsedCornerBracket> cornerBrackets = new HashSet<>();

    @OneToMany(mappedBy = "ghost", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private Set<UsedNonwovenFabric> nonwovenFabrics = new HashSet<>();

    @OneToMany(mappedBy = "ghost", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private Set<UsedPlastic> plastics = new HashSet<>();

    @OneToMany(mappedBy = "ghost", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private Set<UsedPolyethylene> polyethylenes = new HashSet<>();

    @OneToMany(mappedBy = "ghost", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Father> fathers = new ArrayList<>();

    public Ghost(String code, String suffix, String description, Integer measure1, Integer measure2, Integer measure3) {
        this.code = code;
        this.suffix = suffix;
        this.description = description;
        this.measure1 = measure1;
        this.measure2 = measure2;
        this.measure3 = measure3;
    }
}
