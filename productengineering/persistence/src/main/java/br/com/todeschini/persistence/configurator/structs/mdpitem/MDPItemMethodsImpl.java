package br.com.todeschini.persistence.configurator.structs.mdpitem;

import br.com.todeschini.domain.business.configurator.structs.mdpitem.DMDPItem;
import br.com.todeschini.domain.business.configurator.structs.mdpitem.spi.MDPItemMethods;
import br.com.todeschini.domain.business.mdp.mdpson.api.MDPSonService;
import br.com.todeschini.domain.business.mdp.usededgebanding.api.UsedEdgeBandingService;
import br.com.todeschini.domain.business.mdp.usedglue.api.UsedGlueService;
import br.com.todeschini.domain.business.mdp.usedsheet.api.UsedSheetService;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.mdp.MDPSon;
import br.com.todeschini.persistence.entities.mdp.UsedEdgeBanding;
import br.com.todeschini.persistence.entities.mdp.UsedGlue;
import br.com.todeschini.persistence.entities.mdp.UsedSheet;
import br.com.todeschini.persistence.mdp.edgebanding.EdgeBandingRepository;
import br.com.todeschini.persistence.mdp.glue.GlueRepository;
import br.com.todeschini.persistence.mdp.mdpson.MDPSonDomainToEntityAdapter;
import br.com.todeschini.persistence.mdp.mdpson.MDPSonRepository;
import br.com.todeschini.persistence.mdp.sheet.SheetRepository;
import br.com.todeschini.persistence.mdp.usededgebanding.UsedEdgeBandingDomainToEntityAdapter;
import br.com.todeschini.persistence.mdp.usedglue.UsedGlueDomainToEntityAdapter;
import br.com.todeschini.persistence.mdp.usedsheet.UsedSheetDomainToEntityAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MDPItemMethodsImpl implements MDPItemMethods {

    private final SheetRepository sheetRepository;
    private final EdgeBandingRepository edgeBandingRepository;
    private final GlueRepository glueRepository;

    @Autowired
    private MDPSonRepository mdpSonRepository;
    @Autowired
    private UsedSheetService usedSheetService;
    @Autowired
    private UsedSheetDomainToEntityAdapter usedSheetDomainToEntityAdapter;
    @Autowired
    private UsedEdgeBandingService usedEdgeBandingService;
    @Autowired
    private UsedEdgeBandingDomainToEntityAdapter usedEdgeBandingDomainToEntityAdapter;
    @Autowired
    private UsedGlueService usedGlueService;
    @Autowired
    private UsedGlueDomainToEntityAdapter usedGlueDomainToEntityAdapter;
    @Autowired
    private MDPSonService mdpSonService;
    @Autowired
    private MDPSonDomainToEntityAdapter mdpSonDomainToEntityAdapter;

    public MDPItemMethodsImpl(SheetRepository sheetRepository, EdgeBandingRepository edgeBandingRepository, GlueRepository glueRepository) {
        this.sheetRepository = sheetRepository;
        this.edgeBandingRepository = edgeBandingRepository;
        this.glueRepository = glueRepository;
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
                //mdpSonService.update(son.getCode(), mdpSonDomainToEntityAdapter.toDomain(son));
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
        usedSheetService.insert(usedSheetDomainToEntityAdapter.toDomain(usedSheet));
        //usedSheetRepository.save(usedSheet);
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
        usedEdgeBandingService.insert(usedEdgeBandingDomainToEntityAdapter.toDomain(usedEdgeBanding));
        //usedEdgeBandingRepository.save(usedEdgeBanding);
    }

    private void generateUsedGlue(MDPSon son, Long glueCode) {
        UsedGlue usedGlue = new UsedGlue();
        usedGlue.setSon(son);
        usedGlue.setGlue(glueRepository.findById(glueCode).orElseThrow(() -> new ResourceNotFoundException("Cola não encontrada")));
        usedGlue.calculateNetQuantity(son.getEdgeBandings().iterator().next().getNetQuantity()); // Assuming we always have at least one edge banding
        usedGlue.calculateGrossQuantity();
        son.getGlues().add(usedGlue);
        usedGlueService.insert(usedGlueDomainToEntityAdapter.toDomain(usedGlue));
        //usedGlueRepository.save(usedGlue);
    }
}
