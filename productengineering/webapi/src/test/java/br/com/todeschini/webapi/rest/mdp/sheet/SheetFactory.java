package br.com.todeschini.webapi.rest.mdp.sheet;

import br.com.todeschini.domain.business.mdp.sheet.DSheet;
import br.com.todeschini.domain.business.publico.color.DColor;

import java.time.LocalDate;

public class SheetFactory {

    public static DSheet createDSheet() {
        DSheet sheet = new DSheet();
        sheet.setCode(1111111L);
        sheet.setDescription("Sheet");
        sheet.setFamily("abcd");
        sheet.setImplementation(LocalDate.of(3000,1,1));
        sheet.setLostPercentage(1.0);
        sheet.setFaces(1);
        sheet.setThickness(1.0);
        sheet.setColor(new DColor(1L));
        sheet.setMaterialId(1L);
        return sheet;
    }

    public static DSheet createDuplicatedDSheet() {
        DSheet sheet = new DSheet();
        sheet.setCode(2222222L);
        sheet.setDescription("Sheet");
        sheet.setFamily("abcd");
        sheet.setImplementation(LocalDate.of(3000,1,1));
        sheet.setLostPercentage(1.0);
        sheet.setFaces(1);
        sheet.setThickness(1.0);
        sheet.setColor(new DColor(1L));
        sheet.setMaterialId(1L);
        return sheet;
    }

    public static DSheet createNonExistingDSheet(Long nonExistingId) {
        DSheet sheet = new DSheet();
        sheet.setCode(nonExistingId);
        sheet.setDescription("Sheet");
        sheet.setFamily("abcd");
        sheet.setImplementation(LocalDate.of(3000,1,1));
        sheet.setLostPercentage(1.0);
        sheet.setFaces(1);
        sheet.setThickness(1.0);
        sheet.setColor(new DColor(1L));
        sheet.setMaterialId(1L);
        return sheet;
    }
}
