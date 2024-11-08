package br.com.todeschini.persistence.entities.publico;

import br.com.todeschini.persistence.entities.enums.SituacaoEnum;
import br.com.todeschini.persistence.entities.enums.converters.SituacaoEnumConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public class AuditoriaInfo {

    @CreatedBy
    @Size(max = 50)
    @Column(name = "criadopor")
    private String criadopor;

    @CreationTimestamp
    @Column(name = "criadoem")
    private LocalDateTime criadoem;

    @LastModifiedBy
    @Size(max = 50)
    @Column(name = "modificadopor")
    private String modificadopor;

    @LastModifiedDate
    @Column(name = "modificadoem")
    private LocalDateTime modificadoem;

    @NotNull
    @Convert(converter = SituacaoEnumConverter.class)
    private SituacaoEnum situacao = SituacaoEnum.ATIVO;
}
