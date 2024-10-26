package br.com.todeschini.webapi.rest.publico.chapa;

import br.com.todeschini.domain.business.enums.DTipoMaterial;
import br.com.todeschini.domain.business.publico.chapa.DChapa;
import br.com.todeschini.domain.business.publico.cor.DCor;

import java.time.LocalDate;

public class ChapaFactory {

    public static DChapa createDChapa() {
        DChapa chapa = new DChapa();
        chapa.setCodigo(1);
        chapa.setDescricao("Descrição");
        chapa.setTipoMaterial(DTipoMaterial.CHAPA_MDP);
        chapa.setImplantacao(LocalDate.of(3000,1,1));
        chapa.setPorcentagemPerda(12.0);
        chapa.setValor(10.0);
        chapa.setCor(new DCor(1));
        chapa.setEspessura(18);
        chapa.setFaces(2);
        return chapa;
    }

    public static DChapa createDuplicatedDChapa(String duplicatedDescription) {
        DChapa chapa = new DChapa();
        chapa.setCodigo(2);
        chapa.setDescricao(duplicatedDescription);
        chapa.setTipoMaterial(DTipoMaterial.CHAPA_MDP);
        chapa.setImplantacao(LocalDate.of(3000,1,1));
        chapa.setPorcentagemPerda(12.0);
        chapa.setValor(10.0);
        chapa.setCor(new DCor(1));
        chapa.setEspessura(18);
        chapa.setFaces(2);
        return chapa;
    }

    public static DChapa createNonExistingDChapa(Integer nonExistingId) {
        DChapa chapa = new DChapa();
        chapa.setCodigo(nonExistingId);
        chapa.setDescricao("Descrição");
        chapa.setTipoMaterial(DTipoMaterial.CHAPA_MDP);
        chapa.setImplantacao(LocalDate.of(3000,1,1));
        chapa.setPorcentagemPerda(12.0);
        chapa.setValor(10.0);
        chapa.setCor(new DCor(1));
        chapa.setEspessura(18);
        chapa.setFaces(2);
        return chapa;
    }
}
