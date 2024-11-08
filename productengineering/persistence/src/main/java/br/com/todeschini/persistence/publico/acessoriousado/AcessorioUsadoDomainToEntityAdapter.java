package br.com.todeschini.persistence.publico.acessoriousado;

import br.com.todeschini.domain.Convertable;
import br.com.todeschini.domain.business.enums.DSituacaoEnum;
import br.com.todeschini.domain.business.publico.acessoriousado.DAcessorioUsado;
import br.com.todeschini.domain.business.publico.filho.DFilho;
import br.com.todeschini.domain.metadata.EntityAdapter;
import br.com.todeschini.persistence.entities.publico.AcessorioUsado;
import br.com.todeschini.persistence.entities.publico.Filho;
import br.com.todeschini.persistence.publico.acessorio.AcessorioDomainToEntityAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@EntityAdapter(entityClass = AcessorioUsado.class)
public class AcessorioUsadoDomainToEntityAdapter implements Convertable<AcessorioUsado, DAcessorioUsado> {

    @Autowired
    private AcessorioDomainToEntityAdapter acessorioDomainToEntityAdapter;

    @Override
    public AcessorioUsado toEntity(DAcessorioUsado domain) {
        AcessorioUsado acessorioUsado = new AcessorioUsado();
        acessorioUsado.setCdacessorioUsado(domain.getCodigo());
        acessorioUsado.setAcessorio(Optional.ofNullable(domain.getAcessorio())
                .map(acessorio -> acessorioDomainToEntityAdapter.toEntity(acessorio))
                .orElse(null));
        acessorioUsado.setFilho(Optional.ofNullable(domain.getFilho())
                .map(filho -> new Filho(filho.getCodigo()))
                .orElse(null));
        acessorioUsado.setQuantidade(domain.getQuantidade());
        acessorioUsado.setValor(domain.getValor());
        acessorioUsado.setUnidadeMedida(Optional.ofNullable(domain.getUnidadeMedida()).orElse("UN"));
        return acessorioUsado;
    }

    @Override
    public DAcessorioUsado toDomain(AcessorioUsado entity) {
        DAcessorioUsado acessorioUsado = new DAcessorioUsado();
        acessorioUsado.setCodigo(entity.getCdacessorioUsado());
        acessorioUsado.setAcessorio(Optional.ofNullable(entity.getAcessorio())
                .map(acessorio -> acessorioDomainToEntityAdapter.toDomain(acessorio))
                .orElse(null));
        acessorioUsado.setFilho(Optional.ofNullable(entity.getFilho())
                .map(filho -> new DFilho(filho.getCdfilho()))
                .orElse(null));
        acessorioUsado.setQuantidade(entity.getQuantidade());
        acessorioUsado.setValor(entity.getValor());
        acessorioUsado.setUnidadeMedida(Optional.ofNullable(entity.getUnidadeMedida()).orElse("UN"));
        acessorioUsado.setSituacao(DSituacaoEnum.valueOf(entity.getSituacao().name()));
        return acessorioUsado;
    }
}
