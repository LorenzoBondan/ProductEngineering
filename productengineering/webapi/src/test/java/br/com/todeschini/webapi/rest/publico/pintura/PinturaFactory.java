package br.com.todeschini.webapi.rest.publico.pintura;

import br.com.todeschini.domain.business.enums.DTipoMaterial;
import br.com.todeschini.domain.business.enums.DTipoPintura;
import br.com.todeschini.domain.business.publico.cor.DCor;
import br.com.todeschini.domain.business.publico.pintura.DPintura;

import java.time.LocalDate;

public class PinturaFactory {

    public static DPintura createDPintura() {
        DPintura Pintura = new DPintura();
        Pintura.setCodigo(1);
        Pintura.setDescricao("Descrição");
        Pintura.setTipoMaterial(DTipoMaterial.PINTURA);
        Pintura.setImplantacao(LocalDate.of(3000,1,1));
        Pintura.setPorcentagemPerda(12.0);
        Pintura.setValor(10.0);
        Pintura.setCor(new DCor(1));
        Pintura.setTipoPintura(DTipoPintura.ACETINADA);
        return Pintura;
    }

    public static DPintura createDuplicatedDPintura(String duplicatedDescription) {
        DPintura Pintura = new DPintura();
        Pintura.setCodigo(2);
        Pintura.setDescricao(duplicatedDescription);
        Pintura.setTipoMaterial(DTipoMaterial.PINTURA);
        Pintura.setImplantacao(LocalDate.of(3000,1,1));
        Pintura.setPorcentagemPerda(12.0);
        Pintura.setValor(10.0);
        Pintura.setCor(new DCor(1));
        Pintura.setTipoPintura(DTipoPintura.ACETINADA);
        return Pintura;
    }

    public static DPintura createNonExistingDPintura(Integer nonExistingId) {
        DPintura Pintura = new DPintura();
        Pintura.setCodigo(nonExistingId);
        Pintura.setDescricao("Descrição");
        Pintura.setTipoMaterial(DTipoMaterial.PINTURA);
        Pintura.setImplantacao(LocalDate.of(3000,1,1));
        Pintura.setPorcentagemPerda(12.0);
        Pintura.setValor(10.0);
        Pintura.setCor(new DCor(1));
        Pintura.setTipoPintura(DTipoPintura.ACETINADA);
        return Pintura;
    }
}
