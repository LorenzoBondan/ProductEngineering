package br.com.todeschini.persistence.configurator.structs.mdfitem;

import br.com.todeschini.domain.business.configurator.structs.mdfitem.DMDFItem;
import br.com.todeschini.domain.business.configurator.structs.mdfitem.spi.MDFItemMethods;
import br.com.todeschini.domain.exceptions.DatabaseException;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.mdf.*;
import br.com.todeschini.persistence.entities.mdp.Sheet;
import br.com.todeschini.persistence.mdf.back.BackRepository;
import br.com.todeschini.persistence.mdf.painting.PaintingRepository;
import br.com.todeschini.persistence.mdf.paintingborderbackground.PaintingBorderBackgroundRepository;
import br.com.todeschini.persistence.mdf.paintingson.PaintingSonRepository;
import br.com.todeschini.persistence.mdf.paintingtype.PaintingTypeRepository;
import br.com.todeschini.persistence.mdf.polyester.PolyesterRepository;
import br.com.todeschini.persistence.mdf.usedbacksheet.UsedBackSheetRepository;
import br.com.todeschini.persistence.mdf.usedpainting.UsedPaintingRepository;
import br.com.todeschini.persistence.mdf.usedpaintingborderbackground.UsedPaintingBorderBackgroundRepository;
import br.com.todeschini.persistence.mdf.usedpolyester.UsedPolyesterRepository;
import br.com.todeschini.persistence.mdp.sheet.SheetRepository;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NonUniqueResultException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MDFItemMethodsImpl implements MDFItemMethods {

    private final SheetRepository sheetRepository;
    private final PaintingSonRepository paintingSonRepository;
    private final BackRepository backRepository;
    private final PaintingRepository paintingRepository;
    private final PaintingBorderBackgroundRepository paintingBorderBackGroundRepository;
    private final PolyesterRepository polyesterRepository;
    private final PaintingTypeRepository paintingTypeRepository;
    private final UsedBackSheetRepository usedBackSheetRepository;
    private final UsedPaintingRepository usedPaintingRepository;
    private final UsedPaintingBorderBackgroundRepository usedPaintingBorderBackGroundRepository;
    private final UsedPolyesterRepository usedPolyesterRepository;

    public MDFItemMethodsImpl(SheetRepository sheetRepository, PaintingSonRepository paintingSonRepository, BackRepository backRepository, PaintingRepository paintingRepository, PaintingBorderBackgroundRepository paintingBorderBackGroundRepository, PolyesterRepository polyesterRepository, PaintingTypeRepository paintingTypeRepository, UsedBackSheetRepository usedBackSheetRepository, UsedPaintingRepository usedPaintingRepository, UsedPaintingBorderBackgroundRepository usedPaintingBorderBackGroundRepository, UsedPolyesterRepository usedPolyesterRepository) {
        this.sheetRepository = sheetRepository;
        this.paintingSonRepository = paintingSonRepository;
        this.backRepository = backRepository;
        this.paintingRepository = paintingRepository;
        this.paintingBorderBackGroundRepository = paintingBorderBackGroundRepository;
        this.polyesterRepository = polyesterRepository;
        this.paintingTypeRepository = paintingTypeRepository;
        this.usedBackSheetRepository = usedBackSheetRepository;
        this.usedPaintingRepository = usedPaintingRepository;
        this.usedPaintingBorderBackGroundRepository = usedPaintingBorderBackGroundRepository;
        this.usedPolyesterRepository = usedPolyesterRepository;
    }

    @Override
    @Transactional
    public void generateMDFStruct(DMDFItem item) {
        PaintingType paintingType = resolvePaintingType(item.getSatin(), item.getHighShine(), item.getSatinGlass());
        updatePaintingSons(item.getList(), item.getSatin(), item.getHighShine(), item.getSatinGlass(), item.getSpecial(), item.getFaces());
        for(Object obj : item.getList()){
            if(obj instanceof PaintingSon paintingSon) {
                createBackAndLinkToSon(paintingSon);
                createUsedBackSheetAndSave(paintingSon);
                assert paintingType != null;
                createAndLinkUsedPainting(paintingSon, paintingType, item.getHighShine());
                createAndLinkUsedPaintingBorderBackground(paintingSon, item.getPaintingBorderBackgroundId());
                createAndLinkUsedPolyester(paintingSon, item.getPolyesterId());
                paintingSon.calculateValue();
                paintingSonRepository.save(paintingSon);
            }
        }
    }

    private PaintingType resolvePaintingType(Boolean satin, Boolean highShine, Boolean satinGlass) {
        if(satin) {
            return paintingTypeRepository.findByDescription("Satin").iterator().next();
        } else if(highShine) {
            return paintingTypeRepository.findByDescription("High Shine").iterator().next();
        } else if(satinGlass) {
            return paintingTypeRepository.findByDescription("Satin Glass").iterator().next();
        }
        return null;
    }

    private void updatePaintingSons(List<Object> list, Boolean satin, Boolean highShine, Boolean satinGlass,
                                    Boolean special, Integer faces) {
        for(Object obj : list) {
            if(obj instanceof PaintingSon son){
                if(satin) {
                    son.setSatin(true);
                    son.setHighShine(false);
                    son.setSatinGlass(false);
                } else if(highShine) {
                    son.setHighShine(true);
                    son.setSatin(false);
                    son.setSatinGlass(false);
                } else if(satinGlass) {
                    son.setSatinGlass(true);
                    son.setHighShine(false);
                    son.setSatin(false);
                }
                son.setSpecial(special);
                if(faces == 2) {
                    son.setFaces(2);
                }
                son.generateSuffix();
            }
        }
    }

    private void createBackAndLinkToSon(PaintingSon paintingSon) {
        Back back = new Back();
        back.setSuffix(paintingSon.getSuffix());
        back.setCode(Long.parseLong(paintingSon.getCode().toString().substring(0,6) + back.getSuffix().toString()));
        back.setDescription("FUNDO " + paintingSon.getDescription());
        back.setFamily("FND123");
        back.setThickness(paintingSon.getMeasure3().doubleValue());
        back.setMeasure1(paintingSon.getMeasure1());
        back.setMeasure2(paintingSon.getMeasure2());
        paintingSonRepository.save(paintingSon);
        back.getSons().add(paintingSon);
        backRepository.save(back);
        paintingSon.setBack(back);
        backRepository.save(back);
        paintingSonRepository.save(paintingSon);
    }

    private void createUsedBackSheetAndSave(PaintingSon paintingSon) {
        Sheet sheet = sheetRepository.findByThicknessAndFaces(Double.valueOf(paintingSon.getMeasure3()), paintingSon.getFaces());
        if(usedBackSheetRepository.findByBackIdAndSheetId(paintingSon.getBack().getCode(), sheet.getCode()).isEmpty()){
            UsedBackSheet usedBackSheet = new UsedBackSheet();
            usedBackSheet.setBack(paintingSon.getBack());
            usedBackSheet.setSheet(sheet);
            usedBackSheet.calculateNetQuantity();
            usedBackSheet.calculateGrossQuantity();
            paintingSon.getBack().getSheets().add(usedBackSheet);
            usedBackSheetRepository.save(usedBackSheet);
            backRepository.save(paintingSon.getBack());
        }
    }

    private void createAndLinkUsedPainting(PaintingSon paintingSon, PaintingType paintingType, Boolean highShine) {
        Collection<Painting> paintings = paintingRepository.findByColorAndPaintingType(paintingSon.getColor().getCode(), paintingType.getId());
        if(paintings.size() == 1){
            Painting painting = paintingRepository.findByColorAndPaintingType(paintingSon.getColor().getCode(), paintingType.getId()).iterator().next();
            UsedPainting usedPainting1 = createUsedPainting(painting, paintingSon);
            paintingSon.getPaintings().add(usedPainting1);
            if(paintingSon.getFaces() == 2) {
                if(highShine) {
                    Painting painting2 = paintingRepository.findByColorAndPaintingType(paintingSon.getColor().getCode(), 1L).iterator().next();
                    UsedPainting usedPainting2 = createUsedPainting(painting2, paintingSon);
                    paintingSon.getPaintings().add(usedPainting2);
                } else {
                    UsedPainting usedPainting3 = createUsedPainting(painting, paintingSon);
                    paintingSon.getPaintings().add(usedPainting3);
                }
            }
        } else if(paintings.size() > 1){
            String paintingDetails = paintings.stream()
                    .map(p -> "Descrição: " + p.getDescription() + ", Tipo de pintura: " + p.getPaintingType().getDescription())
                    .collect(Collectors.joining(" | "));
            throw new DatabaseException("Foram encontrados mais de um registro para a Pintura contendo a descrição '" +
                    paintingSon.getColor().getName() + "' e o tipo de pintura '" + paintingType.getDescription() +
                    "'. Detalhes das pinturas encontradas: " + paintingDetails);
        } else{
            throw new ResourceNotFoundException("Não foram encontradas Pinturas para a combinação de '" + paintingSon.getColor().getName() + "' e '" + paintingType.getDescription() + "'");
        }
    }

    private UsedPainting createUsedPainting(Painting painting, PaintingSon paintingSon){
        UsedPainting usedPainting = new UsedPainting();
        usedPainting.setPaintingSon(paintingSon);
        usedPainting.setPainting(painting);
        usedPainting.calculateNetQuantity();
        usedPainting.calculateGrossQuantity();
        usedPainting.setPaintingSon(paintingSon);
        usedPaintingRepository.save(usedPainting);
        return usedPainting;
    }

    private void createAndLinkUsedPaintingBorderBackground(PaintingSon paintingSon, Long paintingBorderBackgroundId) {
        UsedPaintingBorderBackground usedPaintingBorderBackground = new UsedPaintingBorderBackground();
        usedPaintingBorderBackground.setPaintingSon(paintingSon);
        usedPaintingBorderBackground.setPaintingBorderBackground(paintingBorderBackGroundRepository.findById(paintingBorderBackgroundId).get());
        usedPaintingBorderBackground.calculateNetQuantity();
        usedPaintingBorderBackground.calculateGrossQuantity();
        paintingSon.getPaintingBorderBackgrounds().add(usedPaintingBorderBackground);
        usedPaintingBorderBackGroundRepository.save(usedPaintingBorderBackground);
    }

    private void createAndLinkUsedPolyester(PaintingSon paintingSon, Long polyesterId) {
        if(polyesterId != null) {
            UsedPolyester usedPolyester = new UsedPolyester();
            usedPolyester.setPaintingSon(paintingSon);
            usedPolyester.setPolyester(polyesterRepository.findById(polyesterId).get());
            usedPolyester.calculateNetQuantity();
            usedPolyester.calculateGrossQuantity();
            paintingSon.getPolyesters().add(usedPolyester);
            usedPolyesterRepository.save(usedPolyester);
        }
    }
}
