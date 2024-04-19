package br.com.todeschini.webapi.rest.mdf.paintingson;

import br.com.todeschini.domain.business.mdf.back.DBack;
import br.com.todeschini.domain.business.mdf.paintingson.DPaintingSon;
import br.com.todeschini.domain.business.publico.color.DColor;

public class PaintingSonFactory {

    public static DPaintingSon createDPaintingSon() {
        DPaintingSon PaintingSon = new DPaintingSon();
        PaintingSon.setCode(111111111L);
        PaintingSon.setDescription("PaintingSon");
        PaintingSon.setMeasure1(1);
        PaintingSon.setMeasure2(1);
        PaintingSon.setMeasure3(1);
        PaintingSon.setMeasurementUnit("UN");
        PaintingSon.setColor(new DColor(1L));
        PaintingSon.setFatherCode(123L);
        PaintingSon.setBack(new DBack(1L));
        PaintingSon.setSatin(true);
        PaintingSon.setSatinGlass(false);
        PaintingSon.setHighShine(false);
        PaintingSon.setSpecial(false);
        PaintingSon.setFaces(1);
        PaintingSon.setSuffix(77);
        return PaintingSon;
    }

    public static DPaintingSon createDuplicatedDPaintingSon() {
        DPaintingSon PaintingSon = new DPaintingSon();
        PaintingSon.setCode(222222222L);
        PaintingSon.setDescription("PaintingSon");
        PaintingSon.setMeasure1(1);
        PaintingSon.setMeasure2(1);
        PaintingSon.setMeasure3(1);
        PaintingSon.setMeasurementUnit("UN");
        PaintingSon.setColor(new DColor(1L));
        PaintingSon.setFatherCode(123L);
        PaintingSon.setBack(new DBack(1L));
        PaintingSon.setSatin(true);
        PaintingSon.setSatinGlass(false);
        PaintingSon.setHighShine(false);
        PaintingSon.setSpecial(false);
        PaintingSon.setFaces(1);
        PaintingSon.setSuffix(77);
        return PaintingSon;
    }

    public static DPaintingSon createNonExistingDPaintingSon(Long nonExistingId) {
        DPaintingSon PaintingSon = new DPaintingSon();
        PaintingSon.setCode(nonExistingId);
        PaintingSon.setDescription("PaintingSon");
        PaintingSon.setMeasure1(1);
        PaintingSon.setMeasure2(1);
        PaintingSon.setMeasure3(1);
        PaintingSon.setMeasurementUnit("UN");
        PaintingSon.setColor(new DColor(1L));
        PaintingSon.setFatherCode(123L);
        PaintingSon.setBack(new DBack(1L));
        PaintingSon.setSatin(true);
        PaintingSon.setSatinGlass(false);
        PaintingSon.setHighShine(false);
        PaintingSon.setSpecial(false);
        PaintingSon.setFaces(1);
        PaintingSon.setSuffix(77);
        return PaintingSon;
    }
}
