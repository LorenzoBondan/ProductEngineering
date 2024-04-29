package br.com.todeschini.persistence;

import br.com.todeschini.domain.business.aluminium.aluminiumson.DAluminiumSon;
import br.com.todeschini.domain.business.aluminium.aluminiumtype.DAluminiumType;
import br.com.todeschini.domain.business.aluminium.drawerpull.DDrawerPull;
import br.com.todeschini.domain.business.aluminium.glass.DGlass;
import br.com.todeschini.domain.business.aluminium.molding.DMolding;
import br.com.todeschini.domain.business.aluminium.screw.DScrew;
import br.com.todeschini.domain.business.aluminium.trysquare.DTrySquare;
import br.com.todeschini.domain.business.aluminium.useddrawerpull.DUsedDrawerPull;
import br.com.todeschini.domain.business.aluminium.usedglass.DUsedGlass;
import br.com.todeschini.domain.business.aluminium.usedmolding.DUsedMolding;
import br.com.todeschini.domain.business.aluminium.usedscrew.DUsedScrew;
import br.com.todeschini.domain.business.aluminium.usedtrysquare.DUsedTrySquare;
import br.com.todeschini.domain.business.configurator.configurators.aluminiumconfigurator.DAluminiumConfigurator;
import br.com.todeschini.domain.business.configurator.generators.fathergenerator.DFatherGenerator;
import br.com.todeschini.domain.business.configurator.generators.ghostgenerator.DGhostGenerator;
import br.com.todeschini.domain.business.configurator.generators.guidegenerator.DGuideGenerator;
import br.com.todeschini.domain.business.guides.guide.DGuide;
import br.com.todeschini.domain.business.guides.guidemachine.DGuideMachine;
import br.com.todeschini.domain.business.guides.machine.DMachine;
import br.com.todeschini.domain.business.guides.machinegroup.DMachineGroup;
import br.com.todeschini.domain.business.mdf.back.DBack;
import br.com.todeschini.domain.business.mdf.painting.DPainting;
import br.com.todeschini.domain.business.mdf.paintingborderbackground.DPaintingBorderBackground;
import br.com.todeschini.domain.business.mdf.paintingson.DPaintingSon;
import br.com.todeschini.domain.business.mdf.paintingtype.DPaintingType;
import br.com.todeschini.domain.business.mdf.polyester.DPolyester;
import br.com.todeschini.domain.business.mdf.usedbacksheet.DUsedBackSheet;
import br.com.todeschini.domain.business.mdf.usedpainting.DUsedPainting;
import br.com.todeschini.domain.business.mdf.usedpaintingborderbackground.DUsedPaintingBorderBackground;
import br.com.todeschini.domain.business.mdf.usedpolyester.DUsedPolyester;
import br.com.todeschini.domain.business.mdp.edgebanding.DEdgeBanding;
import br.com.todeschini.domain.business.mdp.glue.DGlue;
import br.com.todeschini.domain.business.mdp.mdpson.DMDPSon;
import br.com.todeschini.domain.business.mdp.sheet.DSheet;
import br.com.todeschini.domain.business.mdp.usededgebanding.DUsedEdgeBanding;
import br.com.todeschini.domain.business.mdp.usedglue.DUsedGlue;
import br.com.todeschini.domain.business.mdp.usedsheet.DUsedSheet;
import br.com.todeschini.domain.business.packaging.cornerbracket.DCornerBracket;
import br.com.todeschini.domain.business.packaging.ghost.DGhost;
import br.com.todeschini.domain.business.packaging.nonwovenfabric.DNonwovenFabric;
import br.com.todeschini.domain.business.packaging.plastic.DPlastic;
import br.com.todeschini.domain.business.packaging.polyethylene.DPolyethylene;
import br.com.todeschini.domain.business.packaging.usedcornerbracket.DUsedCornerBracket;
import br.com.todeschini.domain.business.packaging.usednonwovenfabric.DUsedNonwovenFabric;
import br.com.todeschini.domain.business.packaging.usedplastic.DUsedPlastic;
import br.com.todeschini.domain.business.packaging.usedpolyethylene.DUsedPolyethylene;
import br.com.todeschini.domain.business.publico.color.DColor;
import br.com.todeschini.domain.business.publico.father.DFather;
import br.com.todeschini.domain.business.publico.material.DMaterial;
import br.com.todeschini.domain.business.publico.son.DSon;
import br.com.todeschini.domain.configurator.AluminiumConfigurator;
import br.com.todeschini.domain.configurator.AluminiumSonConfigurator;
import br.com.todeschini.domain.configurator.ColorConfigurator;
import br.com.todeschini.domain.configurator.ScrewConfigurator;
import br.com.todeschini.persistence.entities.packaging.Ghost;
import br.com.todeschini.persistence.entities.publico.Color;
import br.com.todeschini.persistence.entities.publico.Son;

import java.time.LocalDate;
import java.util.List;

public class Factory {

    public static DColor createDColor() {
        DColor dColor = new DColor();
        dColor.setCode(123L);
        dColor.setName("name");
        return dColor;
    }

    public static DMaterial createDMaterial() {
        DMaterial dMaterial = new DMaterial();
        dMaterial.setId(1L);
        dMaterial.setName("name");
        return dMaterial;
    }

    public static DAluminiumType createDAluminiumType() {
        DAluminiumType dAluminiumType = new DAluminiumType();
        //dAluminiumType.setId(null);
        dAluminiumType.setName("name");
        dAluminiumType.setLessQuantity(5.0);
        return dAluminiumType;
    }

    public static DFather createDFather() {
        DFather dfather = new DFather();
        dfather.setCode(123456789L);
        dfather.setDescription("Description");
        dfather.setColor(createDColor());
        return dfather;
    }

    public static DAluminiumSon createDAluminiumSon() {
        DAluminiumSon dAluminiumSon = new DAluminiumSon();
        dAluminiumSon.setCode(123123123L);
        dAluminiumSon.setDescription("Description");
        dAluminiumSon.setColor(createDColor());
        dAluminiumSon.setAluminiumType(createDAluminiumType());
        dAluminiumSon.setFatherCode(createDFather().getCode());
        return dAluminiumSon;
    }

    public static DDrawerPull createDDrawerPull() {
        DDrawerPull dDrawerPull = new DDrawerPull();
        dDrawerPull.setCode(11111111L);
        dDrawerPull.setDescription("Description");
        return dDrawerPull;
    }

    public static DGlass createDGlass() {
        DGlass dGlass = new DGlass();
        dGlass.setCode(11111112L);
        dGlass.setDescription("Description");
        dGlass.setColor(createDColor());
        return dGlass;
    }

    public static DMolding createDMolding() {
        DMolding dMolding = new DMolding();
        dMolding.setCode(11111113L);
        dMolding.setDescription("Description");
        return dMolding;
    }

    public static DScrew createDScrew() {
        DScrew dScrew = new DScrew();
        dScrew.setCode(11111114L);
        dScrew.setDescription("Description");
        return dScrew;
    }

    public static DTrySquare createDTrySquare() {
        DTrySquare dTrySquare = new DTrySquare();
        dTrySquare.setCode(11111115L);
        dTrySquare.setDescription("Description");
        return dTrySquare;
    }

    public static DUsedDrawerPull createDUsedDrawerPull() {
        DUsedDrawerPull dUsedDrawerPull = new DUsedDrawerPull();
        dUsedDrawerPull.setId(1L);
        dUsedDrawerPull.setDrawerPullCode(createDDrawerPull().getCode());
        dUsedDrawerPull.setAluminiumSonCode(createDAluminiumSon().getCode());
        dUsedDrawerPull.setQuantity(1.0);
        dUsedDrawerPull.setMeasurementUnit("UN");
        return dUsedDrawerPull;
    }

    public static DUsedGlass createDUsedGlass() {
        DUsedGlass dUsedGlass = new DUsedGlass();
        dUsedGlass.setId(1L);
        dUsedGlass.setGlassCode(createDGlass().getCode());
        dUsedGlass.setAluminiumSonCode(createDAluminiumSon().getCode());
        dUsedGlass.setQuantity(1.0);
        dUsedGlass.setMeasurementUnit("UN");
        return dUsedGlass;
    }

    public static DUsedMolding createDUsedMolding() {
        DUsedMolding dUsedMolding = new DUsedMolding();
        dUsedMolding.setId(1L);
        dUsedMolding.setMoldingCode(createDMolding().getCode());
        dUsedMolding.setAluminiumSonCode(createDAluminiumSon().getCode());
        dUsedMolding.setQuantity(1.0);
        dUsedMolding.setMeasurementUnit("UN");
        return dUsedMolding;
    }

    public static DUsedScrew createDUsedScrew() {
        DUsedScrew dUsedScrew = new DUsedScrew();
        dUsedScrew.setId(1L);
        dUsedScrew.setScrewCode(createDScrew().getCode());
        dUsedScrew.setAluminiumSonCode(createDAluminiumSon().getCode());
        dUsedScrew.setQuantity(1.0);
        dUsedScrew.setMeasurementUnit("UN");
        return dUsedScrew;
    }

    public static DUsedTrySquare createDUsedTrySquare() {
        DUsedTrySquare dUsedTrySquare = new DUsedTrySquare();
        dUsedTrySquare.setId(1L);
        dUsedTrySquare.setTrySquareCode(createDTrySquare().getCode());
        dUsedTrySquare.setAluminiumSonCode(createDAluminiumSon().getCode());
        dUsedTrySquare.setQuantity(1.0);
        dUsedTrySquare.setMeasurementUnit("UN");
        return dUsedTrySquare;
    }

    public static DGuide createDGuide() {
        DGuide dGuide = new DGuide();
        dGuide.setImplementation(LocalDate.of(3000,1,1));
        dGuide.setFinalDate(LocalDate.of(3000,1,1));
        return dGuide;
    }

    public static DMachineGroup createDMachineGroup() {
        DMachineGroup dMachineGroup = new DMachineGroup();
        dMachineGroup.setName("Machine group");
        return dMachineGroup;
    }

    public static DMachine createDMachine() {
        DMachine dmachine = new DMachine();
        dmachine.setName("Machine name");
        String[] formula = {"x", "+", "y"};
        dmachine.setFormula(formula);
        return dmachine;
    }

    public static DGuideMachine createDGuideMachine() {
        DGuideMachine dGuideMachine = new DGuideMachine();
        dGuideMachine.setMachineTime(1.0);
        dGuideMachine.setManTime(1.0);
        return dGuideMachine;
    }

    public static DBack createDBack() {
        DBack dBack = new DBack();
        dBack.setCode(77000055L);
        dBack.setDescription("Description");
        dBack.setSuffix(55);
        dBack.setMeasure1(1000);
        dBack.setMeasure2(1000);
        dBack.setThickness(18.0);
        return dBack;
    }

    public static DPaintingType createDPaintingType() {
        DPaintingType dPaintingType = new DPaintingType();
        dPaintingType.setDescription("Description");
        return dPaintingType;
    }

    public static DPainting createDPainting() {
        DPainting dPainting = new DPainting();
        dPainting.setCode(12311111L);
        dPainting.setDescription("Description");
        return dPainting;
    }

    public static DPaintingBorderBackground createDPaintingBorderBackground() {
        DPaintingBorderBackground dPaintingBorderBackground = new DPaintingBorderBackground();
        dPaintingBorderBackground.setCode(12311112L);
        dPaintingBorderBackground.setDescription("Description");
        return dPaintingBorderBackground;
    }

    public static DPolyester createDPolyester() {
        DPolyester dPolyester = new DPolyester();
        dPolyester.setCode(12311113L);
        dPolyester.setDescription("Description");
        return dPolyester;
    }

    public static DPaintingSon createDPaintingSon() {
        DPaintingSon dPaintingSon = new DPaintingSon();
        dPaintingSon.setCode(1231111512L);
        dPaintingSon.setDescription("Description");
        dPaintingSon.setFaces(1);
        dPaintingSon.setSuffix(55);
        dPaintingSon.setSpecial(false);
        dPaintingSon.setSatin(false);
        dPaintingSon.setHighShine(true);
        dPaintingSon.setSatinGlass(false);
        //dPaintingSon.setFatherCode(createDFather().getCode());
        //dPaintingSon.setColor(createDFather().getColor());
        return dPaintingSon;
    }

    public static DUsedBackSheet createDUsedBackSheet() {
        DUsedBackSheet dUsedBackSheet = new DUsedBackSheet();
        dUsedBackSheet.setBackCode(createDBack().getCode());
        dUsedBackSheet.setNetQuantity(1.0);
        dUsedBackSheet.setGrossQuantity(1.0);
        return dUsedBackSheet;
    }

    public static DUsedPainting createDUsedPainting() {
        DUsedPainting dUsedPainting = new DUsedPainting();
        dUsedPainting.setNetQuantity(1.0);
        dUsedPainting.setGrossQuantity(1.0);
        return dUsedPainting;
    }

    public static DUsedPaintingBorderBackground createDUsedPaintingBorderBackground() {
        DUsedPaintingBorderBackground dUsedPaintingBorderBackground = new DUsedPaintingBorderBackground();
        dUsedPaintingBorderBackground.setNetQuantity(1.0);
        dUsedPaintingBorderBackground.setGrossQuantity(1.0);
        return dUsedPaintingBorderBackground;
    }

    public static DUsedPolyester createDUsedPolyester() {
        DUsedPolyester dUsedPolyester = new DUsedPolyester();
        dUsedPolyester.setNetQuantity(1.0);
        dUsedPolyester.setGrossQuantity(1.0);
        return dUsedPolyester;
    }

    public static DEdgeBanding createDEdgeBanding() {
        DEdgeBanding dedgeBanding = new DEdgeBanding();
        dedgeBanding.setCode(44444444L);
        dedgeBanding.setDescription("Description");
        dedgeBanding.setColor(createDColor());
        dedgeBanding.setHeight(10);
        dedgeBanding.setThickness(10);
        dedgeBanding.setLostPercentage(10.0);
        return dedgeBanding;
    }

    public static DGlue createDGlue() {
        DGlue dGlue = new DGlue();
        dGlue.setCode(44444445L);
        dGlue.setDescription("Description");
        dGlue.setGrammage(1.0);
        dGlue.setLostPercentage(10.0);
        return dGlue;
    }

    public static DSheet createDSheet() {
        DSheet dSheet = new DSheet();
        dSheet.setCode(1027034L);
        dSheet.setDescription("Description");
        dSheet.setColor(createDColor());
        dSheet.setThickness(18.0);
        dSheet.setFaces(2);
        dSheet.setLostPercentage(10.0);
        //dSheet.setMaterialId(1L);
        return dSheet;
    }

    public static DSon createDSon() {
        DSon son = new DSon();
        son.setCode(444444998L);
        son.setDescription("Description");
        son.setMeasure1(1000);
        son.setMeasure2(850);
        son.setMeasure3(18);
        son.setColor(createDColor());
        son.setFatherCode(createDFather().getCode());
        return son;
    }

    public static DMDPSon createDMDPSon() {
        DMDPSon dMDPSon = new DMDPSon();
        dMDPSon.setCode(444444999L);
        dMDPSon.setDescription("Description");
        dMDPSon.setMeasure1(1000);
        dMDPSon.setMeasure2(850);
        dMDPSon.setMeasure3(18);
        dMDPSon.setColor(createDColor());
        dMDPSon.setFatherCode(createDFather().getCode());
        return dMDPSon;
    }

    public static DUsedEdgeBanding createDUsedEdgeBanding() {
        DUsedEdgeBanding usedEdgeBanding = new DUsedEdgeBanding();
        usedEdgeBanding.setNetQuantity(1.0);
        usedEdgeBanding.setGrossQuantity(1.0);
        return usedEdgeBanding;
    }

    public static DUsedGlue createDUsedGlue() {
        DUsedGlue dUsedGlue = new DUsedGlue();
        dUsedGlue.setNetQuantity(1.0);
        dUsedGlue.setGrossQuantity(1.0);
        return dUsedGlue;
    }

    public static DUsedSheet createDUsedSheet() {
        DUsedSheet dUsedSheet = new DUsedSheet();
        dUsedSheet.setNetQuantity(1.0);
        dUsedSheet.setGrossQuantity(1.0);
        return dUsedSheet;
    }

    public static DCornerBracket createDCornerBracket() {
        DCornerBracket dCornerBracket = new DCornerBracket();
        dCornerBracket.setCode(88888888L);
        dCornerBracket.setDescription("Description");
        return dCornerBracket;
    }

    public static DNonwovenFabric createDNonwovenFabric() {
        DNonwovenFabric nonwovenFabric = new DNonwovenFabric();
        nonwovenFabric.setCode(88888887L);
        nonwovenFabric.setDescription("Description");
        nonwovenFabric.setLostPercentage(10.0);
        return nonwovenFabric;
    }

    public static DPlastic createDPlastic() {
        DPlastic plastic = new DPlastic();
        plastic.setCode(88888886L);
        plastic.setDescription("Description");
        plastic.setGrammage(1.0);
        plastic.setLostPercentage(10.0);
        return plastic;
    }

    public static DPolyethylene createDPolyethylene() {
        DPolyethylene polyethylene = new DPolyethylene();
        polyethylene.setCode(88888885L);
        polyethylene.setDescription("Description");
        polyethylene.setLostPercentage(10.0);
        return polyethylene;
    }

    public static DGhost createDGhost() {
        DGhost dGhost = new DGhost();
        dGhost.setCode("123456F99");
        dGhost.setDescription("Description");
        dGhost.setSuffix("F99");
        dGhost.setMeasure1(1000);
        dGhost.setMeasure2(750);
        dGhost.setMeasure3(18);
        return dGhost;
    }

    public static DUsedCornerBracket createDUsedCornerBracket() {
        DUsedCornerBracket dUsedCornerBracket = new DUsedCornerBracket();
        dUsedCornerBracket.setNetQuantity(1.0);
        dUsedCornerBracket.setGrossQuantity(1.0);
        return dUsedCornerBracket;
    }

    public static DUsedNonwovenFabric createDUsedNonwovenFabric() {
        DUsedNonwovenFabric nonwovenFabric = new DUsedNonwovenFabric();
        nonwovenFabric.setNetQuantity(1.0);
        nonwovenFabric.setGrossQuantity(1.0);
        return nonwovenFabric;
    }

    public static DUsedPlastic createDUsedPlastic() {
        DUsedPlastic dUsedPlastic = new DUsedPlastic();
        dUsedPlastic.setNetQuantity(1.0);
        dUsedPlastic.setGrossQuantity(1.0);
        return dUsedPlastic;
    }

    public static DUsedPolyethylene createDUsedPolyethylene() {
        DUsedPolyethylene dUsedPolyethylene = new DUsedPolyethylene();
        dUsedPolyethylene.setNetQuantity(1.0);
        dUsedPolyethylene.setGrossQuantity(1.0);
        return dUsedPolyethylene;
    }

    public static DAluminiumConfigurator createDAluminiumConfigurator() {
        DAluminiumConfigurator dAluminiumConfigurator = new DAluminiumConfigurator();
        dAluminiumConfigurator.setCornerBracketCode(createDCornerBracket().getCode());
        dAluminiumConfigurator.setPlasticCode(createDPlastic().getCode());
        dAluminiumConfigurator.setNonwovenFabricCode(createDNonwovenFabric().getCode());
        dAluminiumConfigurator.setPolyethyleneCode(createDPlastic().getCode());
        dAluminiumConfigurator.setUpper(true);
        dAluminiumConfigurator.setAdditional(100.0);
        dAluminiumConfigurator.setWidth(500);
        dAluminiumConfigurator.setQuantity(2);
        dAluminiumConfigurator.setOneFace(true);

        dAluminiumConfigurator.setConfig(createAluminiumConfigurator());
        dAluminiumConfigurator.setAluminiumTypeId(createDAluminiumType().getId());
        dAluminiumConfigurator.setGhostSuffix(createDGhost().getSuffix());
        dAluminiumConfigurator.setDrawerPullCode(createDDrawerPull().getCode());
        dAluminiumConfigurator.setTrySquareCode(createDTrySquare().getCode());
        dAluminiumConfigurator.setTrySquareQuantity(2);
        dAluminiumConfigurator.setMoldingCode(createDMolding().getCode());
        dAluminiumConfigurator.setGlueCode(createDGlue().getCode());
        dAluminiumConfigurator.setImplementation(LocalDate.of(3000,1,1));
        return dAluminiumConfigurator;
    }

    public static AluminiumConfigurator createAluminiumConfigurator() {
        AluminiumConfigurator aluminiumConfigurator = new AluminiumConfigurator();
        aluminiumConfigurator.setItems(List.of(createAluminiumSonConfigurator()));
        aluminiumConfigurator.setColors(List.of(createColorConfigurator()));
        aluminiumConfigurator.setScrews(List.of(createScrewConfigurator()));
        return aluminiumConfigurator;
    }

    public static AluminiumSonConfigurator createAluminiumSonConfigurator() {
        AluminiumSonConfigurator aluminiumSonConfigurator = new AluminiumSonConfigurator();
        aluminiumSonConfigurator.setFatherCode(createDFather().getCode());
        aluminiumSonConfigurator.setAluminiumSonCode(createDAluminiumSon().getCode());
        aluminiumSonConfigurator.setDescription("FRENTE DIR ARM S/DOB 2210X586X18");
        return aluminiumSonConfigurator;
    }

    public static ColorConfigurator createColorConfigurator() {
        ColorConfigurator colorConfigurator = new ColorConfigurator();
        colorConfigurator.setCode(createDColor().getCode());
        colorConfigurator.setName(createDColor().getName());
        return colorConfigurator;
    }

    public static ScrewConfigurator createScrewConfigurator() {
        ScrewConfigurator screwConfigurator = new ScrewConfigurator();
        screwConfigurator.setCode(createDScrew().getCode());
        screwConfigurator.setQuantity(5);
        return screwConfigurator;
    }

    public static DFatherGenerator createDFatherGenerator() {
        DFatherGenerator dFatherGenerator = new DFatherGenerator();
        dFatherGenerator.setFatherCode(createDFather().getCode());
        dFatherGenerator.setFatherDescription("FRENTE DIR ARM S/DOB 2210X586X18");
        dFatherGenerator.setColor(new Color(createDColor().getCode(), createDColor().getName()));
        return dFatherGenerator;
    }

    public static DGhostGenerator createDGhostGenerator() {
        DGhostGenerator dGhostGenerator = new DGhostGenerator();
        dGhostGenerator.setCornerBracketCode(createDCornerBracket().getCode());
        dGhostGenerator.setPlasticCode(createDPlastic().getCode());
        dGhostGenerator.setNonwovenFabricCode(createDNonwovenFabric().getCode());
        dGhostGenerator.setPolyethyleneCode(createDPolyethylene().getCode());
        dGhostGenerator.setUpper(true);
        dGhostGenerator.setAdditional(100.0);
        dGhostGenerator.setWidth(500);
        dGhostGenerator.setQuantity(2);
        dGhostGenerator.setOneFace(true);

        Ghost ghost = new Ghost();
        ghost.setCode(createDGhost().getCode());
        ghost.setDescription(createDGhost().getDescription());
        ghost.setSuffix(createDGhost().getSuffix());
        ghost.setMeasure1(createDGhost().getMeasure1());
        ghost.setMeasure2(createDGhost().getMeasure2());
        ghost.setMeasure3(createDGhost().getMeasure3());
        dGhostGenerator.setGhost(ghost);
        return dGhostGenerator;
    }

    public static DGuideGenerator createDGuideGenerator() {
        DGuideGenerator dGuideGenerator = new DGuideGenerator();

        Son son = new Son();
        son.setCode(createDMDPSon().getCode());
        son.setDescription(createDMDPSon().getDescription());
        son.setMeasure1(createDMDPSon().getMeasure1());
        son.setMeasure2(createDMDPSon().getMeasure2());
        son.setMeasure3(createDMDPSon().getMeasure3());
        son.setColor(new Color(createDColor().getCode(), createDColor().getName()));
        dGuideGenerator.setSon(son);

        dGuideGenerator.setMachinesIds(List.of(1L));
        dGuideGenerator.setImplementation(LocalDate.of(3000,1,1));
        dGuideGenerator.setFinalDate(LocalDate.of(3000,1,1));

        return dGuideGenerator;
    }
}
