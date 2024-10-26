package br.com.todeschini.persistence.publico.grupomaquina;

import br.com.todeschini.domain.Convertable;
import br.com.todeschini.domain.business.enums.DSituacao;
import br.com.todeschini.domain.business.publico.grupomaquina.DGrupoMaquina;
import br.com.todeschini.domain.metadata.EntityAdapter;
import br.com.todeschini.persistence.entities.publico.GrupoMaquina;
import org.springframework.stereotype.Component;

@Component
@EntityAdapter(entityClass = GrupoMaquina.class)
public class GrupoMaquinaDomainToEntityAdapter implements Convertable<GrupoMaquina, DGrupoMaquina> {

    @Override
    public GrupoMaquina toEntity(DGrupoMaquina domain) {
        return GrupoMaquina.builder()
                .cdgrupoMaquina(domain.getCodigo())
                .nome(domain.getNome())
                .build();
    }

    @Override
    public DGrupoMaquina toDomain(GrupoMaquina entity) {
        return DGrupoMaquina.builder()
                .codigo(entity.getCdgrupoMaquina())
                .nome(entity.getNome())
                .situacao(DSituacao.valueOf(entity.getSituacao().name()))
                .build();
    }
}
