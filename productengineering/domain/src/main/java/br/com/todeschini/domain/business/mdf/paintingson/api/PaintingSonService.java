package br.com.todeschini.domain.business.mdf.paintingson.api;

import org.springframework.stereotype.Component;

@Component
public interface PaintingSonService extends FindPaintingSon, InsertPaintingSon, UpdatePaintingSon, DeletePaintingSon, InactivatePaintingSon,
        FindAllActivePaintingSonAndCurrentOne {
}
