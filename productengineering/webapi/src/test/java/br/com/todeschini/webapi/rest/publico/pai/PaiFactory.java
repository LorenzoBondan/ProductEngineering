package br.com.todeschini.webapi.rest.publico.pai;

import br.com.todeschini.domain.business.publico.pai.DPai;

public class PaiFactory {

    public static DPai createDPai() {
        DPai pai = new DPai();
        pai.setCodigo(1);
        pai.setDescricao("Descrição");
        pai.setBordasComprimento(1);
        pai.setBordasLargura(1);
        pai.setNumeroCantoneiras(4);
        pai.setTntUmaFace(true);
        pai.setLarguraPlastico(100);
        pai.setPlasticoAcima(true);
        pai.setPlasticoAdicional(100.0);
        return pai;
    }

    public static DPai createDuplicatedDPai(String duplicatedDescription) {
        DPai pai = new DPai();
        pai.setCodigo(2);
        pai.setDescricao(duplicatedDescription);
        pai.setBordasComprimento(1);
        pai.setBordasLargura(1);
        pai.setNumeroCantoneiras(4);
        pai.setTntUmaFace(true);
        pai.setLarguraPlastico(100);
        pai.setPlasticoAcima(true);
        pai.setPlasticoAdicional(100.0);
        return pai;
    }

    public static DPai createNonExistingDPai(Integer nonExistingId) {
        DPai pai = new DPai();
        pai.setCodigo(nonExistingId);
        pai.setDescricao("Descrição");
        pai.setBordasComprimento(1);
        pai.setBordasLargura(1);
        pai.setNumeroCantoneiras(4);
        pai.setTntUmaFace(true);
        pai.setLarguraPlastico(100);
        pai.setPlasticoAcima(true);
        pai.setPlasticoAdicional(100.0);
        return pai;
    }
}
