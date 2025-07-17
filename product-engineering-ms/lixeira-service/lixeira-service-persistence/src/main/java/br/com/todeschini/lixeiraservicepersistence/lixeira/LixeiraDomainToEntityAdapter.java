package br.com.todeschini.lixeiraservicepersistence.lixeira;

import br.com.todeschini.libauditdomain.enums.DSituacaoEnum;
import br.com.todeschini.libauditpersistence.entities.enums.SituacaoEnum;
import br.com.todeschini.libvalidationhandler.contracts.Convertable;
import br.com.todeschini.lixeiraservicedomain.lixeira.DLixeira;
import br.com.todeschini.lixeiraservicepersistence.entities.Lixeira;
import org.springframework.stereotype.Component;

@Component
public class LixeiraDomainToEntityAdapter implements Convertable<Lixeira, DLixeira> {

    @Override
    public Lixeira toEntity(DLixeira domain) {
        return Lixeira.builder()
                .id(domain.getId())
                .nometabela(domain.getNometabela())
                .data(domain.getData())
                .entidadeid(domain.getEntidadeid())
                .usuario(domain.getUsuario())
                .tabela(domain.getTabela())
                .situacao(SituacaoEnum.valueOf(domain.getSituacao().name()))
                .build();
    }

    @Override
    public DLixeira toDomain(Lixeira entity) {
        return DLixeira.builder()
                .id(entity.getId())
                .nometabela(entity.getNometabela())
                .data(entity.getData())
                .entidadeid(entity.getEntidadeid())
                .usuario(entity.getUsuario())
                .tabela(entity.getTabela())
                .situacao(DSituacaoEnum.valueOf(entity.getSituacao().name()))
                .build();
    }
}
