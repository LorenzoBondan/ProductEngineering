package br.com.todeschini.webapi.rest.packaging.plastic;

import br.com.todeschini.domain.business.packaging.plastic.DPlastic;

import java.time.LocalDate;

public class PlasticFactory {

    public static DPlastic createDPlastic() {
        DPlastic plastic = new DPlastic();
        plastic.setCode(1111111L);
        plastic.setDescription("Plastic");
        plastic.setFamily("abcd");
        plastic.setImplementation(LocalDate.of(3000,1,1));
        plastic.setLostPercentage(1.0);
        plastic.setColor(null);
        plastic.setGrammage(1.0);
        return plastic;
    }

    public static DPlastic createDuplicatedDPlastic() {
        DPlastic plastic = new DPlastic();
        plastic.setCode(2222222L);
        plastic.setDescription("Plastic");
        plastic.setFamily("abcd");
        plastic.setImplementation(LocalDate.of(3000,1,1));
        plastic.setLostPercentage(1.0);
        plastic.setColor(null);
        plastic.setGrammage(1.0);
        return plastic;
    }

    public static DPlastic createNonExistingDPlastic(Long nonExistingId) {
        DPlastic plastic = new DPlastic();
        plastic.setCode(nonExistingId);
        plastic.setDescription("Plastic");
        plastic.setFamily("abcd");
        plastic.setImplementation(LocalDate.of(3000,1,1));
        plastic.setLostPercentage(1.0);
        plastic.setColor(null);
        plastic.setGrammage(1.0);
        return plastic;
    }
}
