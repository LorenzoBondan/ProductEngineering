package br.com.todeschini.persistence.entities.audit;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;

@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "t_history")
public class T_History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime tstamp;
    @Column(columnDefinition = "TEXT")
    private String schemaname;
    @Column(columnDefinition = "TEXT")
    private String tabname;
    @Column(columnDefinition = "TEXT")
    private String operation;
    @Type(type = "jsonb") // ON TEST PROFILE, CHANGE TO json TO AVOID LOG ERROR WHEN APPLICATION STARTS
    @Column(unique = true, columnDefinition = "jsonb")
    private Map<String, Object> old_val;
}
