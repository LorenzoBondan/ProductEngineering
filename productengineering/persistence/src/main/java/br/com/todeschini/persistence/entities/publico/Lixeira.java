package br.com.todeschini.persistence.entities.publico;

import br.com.todeschini.persistence.entities.enums.Situacao;
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
@Table(name = "tb_lixeira", schema = "public")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Lixeira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    @Size(max = 50)
    private String nometabela;
    @NotNull
    private LocalDateTime data;
    @NotNull
    @Type(type = "jsonb") // ON TEST PROFILE, CHANGE TO json TO AVOID LOG ERROR WHEN APPLICATION STARTS
    @Column(unique = true, columnDefinition = "jsonb")
    private Map<String, Object> entidadeid;
    @NotNull
    @Size(max = 50)
    private String usuario;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Situacao situacao;
    @NotNull
    @Size(max = 200)
    private String tabela;
}
