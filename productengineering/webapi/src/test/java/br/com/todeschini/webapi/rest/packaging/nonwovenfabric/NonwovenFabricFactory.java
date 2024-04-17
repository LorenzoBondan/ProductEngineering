package br.com.todeschini.webapi.rest.packaging.nonwovenfabric;

import br.com.todeschini.domain.business.packaging.nonwovenfabric.DNonwovenFabric;

import java.time.LocalDate;

public class NonwovenFabricFactory {

    public static DNonwovenFabric createDNonwovenFabric() {
        DNonwovenFabric nonwovenFabric = new DNonwovenFabric();
        nonwovenFabric.setCode(1L);
        nonwovenFabric.setDescription("NonwovenFabric");
        nonwovenFabric.setFamily("abcd");
        nonwovenFabric.setImplementation(LocalDate.of(3000,1,1));
        nonwovenFabric.setLostPercentage(1.0);
        nonwovenFabric.setColor(null);
        return nonwovenFabric;
    }

    public static DNonwovenFabric createDuplicatedDNonwovenFabric() {
        DNonwovenFabric nonwovenFabric = new DNonwovenFabric();
        nonwovenFabric.setCode(2L);
        nonwovenFabric.setDescription("NonwovenFabric");
        nonwovenFabric.setFamily("abcd");
        nonwovenFabric.setImplementation(LocalDate.of(3000,1,1));
        nonwovenFabric.setLostPercentage(1.0);
        nonwovenFabric.setColor(null);
        return nonwovenFabric;
    }

    public static DNonwovenFabric createNonExistingDNonwovenFabric(Long nonExistingId) {
        DNonwovenFabric nonwovenFabric = new DNonwovenFabric();
        nonwovenFabric.setCode(nonExistingId);
        nonwovenFabric.setDescription("NonwovenFabric");
        nonwovenFabric.setFamily("abcd");
        nonwovenFabric.setImplementation(LocalDate.of(3000,1,1));
        nonwovenFabric.setLostPercentage(1.0);
        nonwovenFabric.setColor(null);
        return nonwovenFabric;
    }
}
