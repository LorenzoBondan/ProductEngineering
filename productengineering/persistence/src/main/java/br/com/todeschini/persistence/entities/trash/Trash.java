package br.com.todeschini.persistence.entities.trash;

import br.com.todeschini.persistence.entities.enums.Status;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "trash", schema = "public")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Trash {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(max = 50)
    private String tableName;
    @NotNull
    private LocalDateTime date;
    @NotNull
    @Type(type = "jsonb") // ON TEST PROFILE, CHANGE TO json TO AVOID LOG ERROR WHEN APPLICATION STARTS
    @Column(unique = true, columnDefinition = "jsonb")
    private Map<String, Object> entityId;
    @NotNull
    @Size(max = 50)
    private String username;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;
    @NotNull
    @Size(max = 200)
    private String referencedTable;
}
