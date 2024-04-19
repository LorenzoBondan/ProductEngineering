package br.com.todeschini.webapi.rest.mdp.glue;

import br.com.todeschini.domain.business.mdp.glue.DGlue;
import br.com.todeschini.domain.business.publico.color.DColor;

import java.time.LocalDate;

public class GlueFactory {

    public static DGlue createDGlue() {
        DGlue glue = new DGlue();
        glue.setCode(1111111L);
        glue.setDescription("Glue");
        glue.setFamily("abcd");
        glue.setImplementation(LocalDate.of(3000,1,1));
        glue.setLostPercentage(1.0);
        glue.setGrammage(1.0);
        glue.setColor(new DColor(1L));
        return glue;
    }

    public static DGlue createDuplicatedDGlue() {
        DGlue glue = new DGlue();
        glue.setCode(2222222L);
        glue.setDescription("Glue");
        glue.setFamily("abcd");
        glue.setImplementation(LocalDate.of(3000,1,1));
        glue.setLostPercentage(1.0);
        glue.setGrammage(1.0);
        glue.setColor(new DColor(1L));
        return glue;
    }

    public static DGlue createNonExistingDGlue(Long nonExistingId) {
        DGlue glue = new DGlue();
        glue.setCode(nonExistingId);
        glue.setDescription("Glue");
        glue.setFamily("abcd");
        glue.setImplementation(LocalDate.of(3000,1,1));
        glue.setLostPercentage(1.0);
        glue.setGrammage(1.0);
        glue.setColor(new DColor(1L));
        return glue;
    }
}
