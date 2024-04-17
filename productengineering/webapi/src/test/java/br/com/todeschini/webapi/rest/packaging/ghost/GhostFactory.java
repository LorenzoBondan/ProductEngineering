package br.com.todeschini.webapi.rest.packaging.ghost;

import br.com.todeschini.domain.business.packaging.ghost.DGhost;

public class GhostFactory {

    public static DGhost createDGhost() {
        DGhost ghost = new DGhost();
        ghost.setCode("220000F99");
        ghost.setSuffix("F99");
        ghost.setDescription("Ghost");
        ghost.setMeasure1(1);
        ghost.setMeasure2(1);
        ghost.setMeasure3(1);
        return ghost;
    }

    public static DGhost createDuplicatedDGhost() {
        DGhost ghost = new DGhost();
        ghost.setCode("220001F99");
        ghost.setSuffix("F99");
        ghost.setDescription("Ghost");
        ghost.setMeasure1(1);
        ghost.setMeasure2(1);
        ghost.setMeasure3(1);
        return ghost;
    }

    public static DGhost createNonExistingDGhost(String nonExistingId) {
        DGhost ghost = new DGhost();
        ghost.setCode(nonExistingId);
        ghost.setSuffix("F99");
        ghost.setDescription("Ghost");
        ghost.setMeasure1(1);
        ghost.setMeasure2(1);
        ghost.setMeasure3(1);
        return ghost;
    }
}
