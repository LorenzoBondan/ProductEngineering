package br.com.todeschini.domain.business.mdf.painting.api;

import org.springframework.stereotype.Component;

@Component
public interface PaintingService extends FindPainting, InsertPainting, UpdatePainting, DeletePainting, InactivatePainting,
        FindAllActivePaintingAndCurrentOne {
}
