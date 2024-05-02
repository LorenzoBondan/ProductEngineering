package br.com.todeschini.persistence.configurator.structs.mdpitem;

import br.com.todeschini.domain.business.configurator.structs.mdpitem.DMDPItem;
import br.com.todeschini.domain.business.configurator.structs.mdpitem.spi.MDPItemMethods;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.mdp.MDPSon;
import br.com.todeschini.persistence.entities.mdp.UsedEdgeBanding;
import br.com.todeschini.persistence.entities.mdp.UsedGlue;
import br.com.todeschini.persistence.entities.mdp.UsedSheet;
import br.com.todeschini.persistence.mdp.edgebanding.EdgeBandingRepository;
import br.com.todeschini.persistence.mdp.glue.GlueRepository;
import br.com.todeschini.persistence.mdp.mdpson.MDPSonRepository;
import br.com.todeschini.persistence.mdp.sheet.SheetRepository;
import br.com.todeschini.persistence.mdp.usededgebanding.UsedEdgeBandingRepository;
import br.com.todeschini.persistence.mdp.usedglue.UsedGlueRepository;
import br.com.todeschini.persistence.mdp.usedsheet.UsedSheetRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MDPItemMethodsImpl implements MDPItemMethods {

    private final MDPSonRepository mdpSonRepository;
    private final SheetRepository sheetRepository;
    private final EdgeBandingRepository edgeBandingRepository;
    private final GlueRepository glueRepository;
    private final UsedSheetRepository usedSheetRepository;
    private final UsedEdgeBandingRepository usedEdgeBandingRepository;
    private final UsedGlueRepository usedGlueRepository;

    public MDPItemMethodsImpl(MDPSonRepository mdpSonRepository, SheetRepository sheetRepository, EdgeBandingRepository edgeBandingRepository, GlueRepository glueRepository, UsedSheetRepository usedSheetRepository, UsedEdgeBandingRepository usedEdgeBandingRepository, UsedGlueRepository usedGlueRepository) {
        this.mdpSonRepository = mdpSonRepository;
        this.sheetRepository = sheetRepository;
        this.edgeBandingRepository = edgeBandingRepository;
        this.glueRepository = glueRepository;
        this.usedSheetRepository = usedSheetRepository;
        this.usedEdgeBandingRepository = usedEdgeBandingRepository;
        this.usedGlueRepository = usedGlueRepository;
    }

    @Override
    @Transactional
    public void generateMDPStruct(DMDPItem item) {
        for (Object obj : item.getList()) {
            if (obj instanceof MDPSon son) {
                generateUsedSheet(son);
                generateUsedEdgeBanding(son, item.getEdgeLength(), item.getEdgeWidth());
                generateUsedGlue(son, item.getGlueCode());
                son.calculateValue();
                mdpSonRepository.save(son);
            }
        }
    }

    private void generateUsedSheet(MDPSon son) {
        UsedSheet usedSheet = new UsedSheet();
        usedSheet.setSon(son);
        usedSheet.setSheet(sheetRepository.getByColorIdAndThickness(son.getColor().getCode(), son.getMeasure3()));
        usedSheet.calculateNetQuantity();
        usedSheet.calculateGrossQuantity();
        son.getSheets().add(usedSheet);
        usedSheetRepository.save(usedSheet);
    }

    private void generateUsedEdgeBanding(MDPSon son, Integer edgeLength, Integer edgeWidth) {
        UsedEdgeBanding usedEdgeBanding = new UsedEdgeBanding();
        usedEdgeBanding.setSon(son);
        usedEdgeBanding.setEdgeBanding(edgeBandingRepository.getByColorId(son.getColor().getCode()));
        usedEdgeBanding.setEdgeLength(edgeLength);
        usedEdgeBanding.setEdgeWidth(edgeWidth);
        usedEdgeBanding.calculateNetQuantity();
        usedEdgeBanding.calculateGrossQuantity();
        son.getEdgeBandings().add(usedEdgeBanding);
        usedEdgeBandingRepository.save(usedEdgeBanding);
    }

    private void generateUsedGlue(MDPSon son, Long glueCode) {
        UsedGlue usedGlue = new UsedGlue();
        usedGlue.setSon(son);
        usedGlue.setGlue(glueRepository.findById(glueCode).orElseThrow(() -> new ResourceNotFoundException("Cola não encontrada")));
        usedGlue.calculateNetQuantity(son.getEdgeBandings().iterator().next().getNetQuantity()); // Assuming we always have at least one edge banding
        usedGlue.calculateGrossQuantity();
        son.getGlues().add(usedGlue);
        usedGlueRepository.save(usedGlue);
    }
}
