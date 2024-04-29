package br.com.todeschini.persistence;

import br.com.todeschini.domain.business.aluminium.aluminiumson.DAluminiumSon;
import br.com.todeschini.domain.business.aluminium.aluminiumson.spi.CrudAluminiumSon;
import br.com.todeschini.domain.business.aluminium.aluminiumtype.DAluminiumType;
import br.com.todeschini.domain.business.aluminium.aluminiumtype.spi.CrudAluminiumType;
import br.com.todeschini.domain.business.aluminium.drawerpull.DDrawerPull;
import br.com.todeschini.domain.business.aluminium.drawerpull.spi.CrudDrawerPull;
import br.com.todeschini.domain.business.aluminium.glass.DGlass;
import br.com.todeschini.domain.business.aluminium.glass.spi.CrudGlass;
import br.com.todeschini.domain.business.aluminium.molding.DMolding;
import br.com.todeschini.domain.business.aluminium.molding.spi.CrudMolding;
import br.com.todeschini.domain.business.aluminium.screw.DScrew;
import br.com.todeschini.domain.business.aluminium.screw.spi.CrudScrew;
import br.com.todeschini.domain.business.aluminium.trysquare.spi.CrudTrySquare;
import br.com.todeschini.domain.business.aluminium.useddrawerpull.DUsedDrawerPull;
import br.com.todeschini.domain.business.aluminium.useddrawerpull.spi.CrudUsedDrawerPull;
import br.com.todeschini.domain.business.aluminium.usedglass.DUsedGlass;
import br.com.todeschini.domain.business.aluminium.usedglass.spi.CrudUsedGlass;
import br.com.todeschini.domain.business.aluminium.usedmolding.DUsedMolding;
import br.com.todeschini.domain.business.aluminium.usedmolding.spi.CrudUsedMolding;
import br.com.todeschini.domain.business.aluminium.usedscrew.DUsedScrew;
import br.com.todeschini.domain.business.aluminium.usedscrew.spi.CrudUsedScrew;
import br.com.todeschini.domain.business.aluminium.usedtrysquare.DUsedTrySquare;
import br.com.todeschini.domain.business.aluminium.usedtrysquare.spi.CrudUsedTrySquare;
import br.com.todeschini.domain.business.configurator.configurators.aluminiumconfigurator.spi.AluminiumConfiguratorMethods;
import br.com.todeschini.domain.business.configurator.generators.fathergenerator.spi.FatherGeneratorMethods;
import br.com.todeschini.domain.business.configurator.generators.ghostgenerator.spi.GhostGeneratorMethods;
import br.com.todeschini.domain.business.configurator.generators.guidegenerator.DGuideGenerator;
import br.com.todeschini.domain.business.configurator.generators.guidegenerator.spi.GuideGeneratorMethods;
import br.com.todeschini.domain.business.guides.guide.DGuide;
import br.com.todeschini.domain.business.guides.guide.spi.CrudGuide;
import br.com.todeschini.domain.business.guides.guidemachine.DGuideMachine;
import br.com.todeschini.domain.business.guides.guidemachine.spi.CrudGuideMachine;
import br.com.todeschini.domain.business.guides.machine.DMachine;
import br.com.todeschini.domain.business.guides.machine.spi.CrudMachine;
import br.com.todeschini.domain.business.guides.machinegroup.DMachineGroup;
import br.com.todeschini.domain.business.guides.machinegroup.spi.CrudMachineGroup;
import br.com.todeschini.domain.business.mdf.back.DBack;
import br.com.todeschini.domain.business.mdf.back.spi.CrudBack;
import br.com.todeschini.domain.business.mdf.painting.DPainting;
import br.com.todeschini.domain.business.mdf.painting.spi.CrudPainting;
import br.com.todeschini.domain.business.mdf.paintingborderbackground.DPaintingBorderBackground;
import br.com.todeschini.domain.business.mdf.paintingborderbackground.spi.CrudPaintingBorderBackground;
import br.com.todeschini.domain.business.mdf.paintingson.DPaintingSon;
import br.com.todeschini.domain.business.mdf.paintingson.spi.CrudPaintingSon;
import br.com.todeschini.domain.business.mdf.paintingtype.DPaintingType;
import br.com.todeschini.domain.business.mdf.paintingtype.spi.CrudPaintingType;
import br.com.todeschini.domain.business.mdf.polyester.DPolyester;
import br.com.todeschini.domain.business.mdf.polyester.spi.CrudPolyester;
import br.com.todeschini.domain.business.mdf.usedbacksheet.DUsedBackSheet;
import br.com.todeschini.domain.business.mdf.usedbacksheet.spi.CrudUsedBackSheet;
import br.com.todeschini.domain.business.mdf.usedpainting.DUsedPainting;
import br.com.todeschini.domain.business.mdf.usedpainting.spi.CrudUsedPainting;
import br.com.todeschini.domain.business.mdf.usedpaintingborderbackground.DUsedPaintingBorderBackground;
import br.com.todeschini.domain.business.mdf.usedpaintingborderbackground.spi.CrudUsedPaintingBorderBackground;
import br.com.todeschini.domain.business.mdf.usedpolyester.DUsedPolyester;
import br.com.todeschini.domain.business.mdf.usedpolyester.spi.CrudUsedPolyester;
import br.com.todeschini.domain.business.mdp.edgebanding.DEdgeBanding;
import br.com.todeschini.domain.business.mdp.edgebanding.spi.CrudEdgeBanding;
import br.com.todeschini.domain.business.mdp.glue.DGlue;
import br.com.todeschini.domain.business.mdp.glue.spi.CrudGlue;
import br.com.todeschini.domain.business.mdp.mdpson.DMDPSon;
import br.com.todeschini.domain.business.mdp.mdpson.spi.CrudMDPSon;
import br.com.todeschini.domain.business.mdp.sheet.DSheet;
import br.com.todeschini.domain.business.mdp.sheet.spi.CrudSheet;
import br.com.todeschini.domain.business.mdp.usededgebanding.DUsedEdgeBanding;
import br.com.todeschini.domain.business.mdp.usededgebanding.spi.CrudUsedEdgeBanding;
import br.com.todeschini.domain.business.mdp.usedglue.DUsedGlue;
import br.com.todeschini.domain.business.mdp.usedglue.spi.CrudUsedGlue;
import br.com.todeschini.domain.business.mdp.usedsheet.DUsedSheet;
import br.com.todeschini.domain.business.mdp.usedsheet.spi.CrudUsedSheet;
import br.com.todeschini.domain.business.packaging.cornerbracket.DCornerBracket;
import br.com.todeschini.domain.business.packaging.cornerbracket.spi.CrudCornerBracket;
import br.com.todeschini.domain.business.packaging.ghost.DGhost;
import br.com.todeschini.domain.business.packaging.ghost.spi.CrudGhost;
import br.com.todeschini.domain.business.packaging.nonwovenfabric.DNonwovenFabric;
import br.com.todeschini.domain.business.packaging.nonwovenfabric.spi.CrudNonwovenFabric;
import br.com.todeschini.domain.business.packaging.plastic.DPlastic;
import br.com.todeschini.domain.business.packaging.plastic.spi.CrudPlastic;
import br.com.todeschini.domain.business.packaging.polyethylene.DPolyethylene;
import br.com.todeschini.domain.business.packaging.polyethylene.spi.CrudPolyethylene;
import br.com.todeschini.domain.business.packaging.usedcornerbracket.DUsedCornerBracket;
import br.com.todeschini.domain.business.packaging.usedcornerbracket.spi.CrudUsedCornerBracket;
import br.com.todeschini.domain.business.packaging.usednonwovenfabric.DUsedNonwovenFabric;
import br.com.todeschini.domain.business.packaging.usednonwovenfabric.spi.CrudUsedNonwovenFabric;
import br.com.todeschini.domain.business.packaging.usedplastic.DUsedPlastic;
import br.com.todeschini.domain.business.packaging.usedplastic.spi.CrudUsedPlastic;
import br.com.todeschini.domain.business.packaging.usedpolyethylene.DUsedPolyethylene;
import br.com.todeschini.domain.business.packaging.usedpolyethylene.spi.CrudUsedPolyethylene;
import br.com.todeschini.domain.business.publico.color.DColor;
import br.com.todeschini.domain.business.publico.color.spi.CrudColor;
import br.com.todeschini.domain.business.publico.father.DFather;
import br.com.todeschini.domain.business.publico.father.spi.CrudFather;
import br.com.todeschini.domain.business.publico.material.DMaterial;
import br.com.todeschini.domain.business.publico.material.spi.CrudMaterial;
import br.com.todeschini.domain.business.publico.son.DSon;
import br.com.todeschini.domain.business.publico.son.spi.CrudSon;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.publico.Father;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest(classes = PersistenceApplicationConfigTests.class)
@EnableAutoConfiguration
@Transactional
class PersistenceApplicationTests {

    @Autowired
    private CrudColor crudColor;
	@Autowired
	private CrudMaterial crudMaterial;
	@Autowired
	private CrudAluminiumSon crudAluminiumSon;
	@Autowired
	private CrudAluminiumType crudAluminiumType;
	@Autowired
	private CrudFather crudFather;
	@Autowired
	private CrudDrawerPull crudDrawerPull;
	@Autowired
	private CrudGlass crudGlass;
	@Autowired
	private CrudMolding crudMolding;
	@Autowired
	private CrudScrew crudScrew;
	@Autowired
	private CrudTrySquare crudTrySquare;
	@Autowired
	private CrudUsedDrawerPull crudUsedDrawerPull;
	@Autowired
	private CrudUsedGlass crudUsedGlass;
	@Autowired
	private CrudUsedMolding crudUsedMolding;
	@Autowired
	private CrudUsedScrew crudUsedScrew;
	@Autowired
	private CrudUsedTrySquare crudUsedTrySquare;
	@Autowired
	private CrudGuide crudGuide;
	@Autowired
	private CrudMachineGroup crudMachineGroup;
	@Autowired
	private CrudMachine crudMachine;
	@Autowired
	private CrudGuideMachine crudGuideMachine;
	@Autowired
	private CrudBack crudBack;
	@Autowired
	private CrudPainting crudPainting;
	@Autowired
	private CrudPaintingBorderBackground crudPaintingBorderBackground;
	@Autowired
	private CrudPaintingSon crudPaintingSon;
	@Autowired
	private CrudPaintingType crudPaintingType;
	@Autowired
	private CrudPolyester crudPolyester;
	@Autowired
	private CrudUsedBackSheet crudUsedBackSheet;
	@Autowired
	private CrudUsedPainting crudUsedPainting;
	@Autowired
	private CrudUsedPaintingBorderBackground crudUsedPaintingBorderBackground;
	@Autowired
	private CrudUsedPolyester crudUsedPolyester;
	@Autowired
	private CrudEdgeBanding crudEdgeBanding;
	@Autowired
	private CrudGlue crudGlue;
	@Autowired
	private CrudMDPSon crudMDPSon;
	@Autowired
	private CrudSheet crudSheet;
	@Autowired
	private CrudUsedEdgeBanding crudUsedEdgeBanding;
	@Autowired
	private CrudUsedGlue crudUsedGlue;
	@Autowired
	private CrudUsedSheet crudUsedSheet;
	@Autowired
	private CrudCornerBracket crudCornerBracket;
	@Autowired
	private CrudGhost crudGhost;
	@Autowired
	private CrudNonwovenFabric crudNonwovenFabric;
	@Autowired
	private CrudPlastic crudPlastic;
	@Autowired
	private CrudPolyethylene crudPolyethylene;
	@Autowired
	private CrudUsedCornerBracket crudUsedCornerBracket;
	@Autowired
	private CrudUsedNonwovenFabric crudUsedNonwovenFabric;
	@Autowired
	private CrudUsedPlastic crudUsedPlastic;
	@Autowired
	private CrudUsedPolyethylene crudUsedPolyethylene;
	@Autowired
	private CrudSon crudSon;

	@Autowired
	private FatherGeneratorMethods fatherGeneratorMethods;
	@Autowired
	private GhostGeneratorMethods ghostGeneratorMethods;
	@Autowired
	private GuideGeneratorMethods guideGeneratorMethods;

	@Autowired
	private AluminiumConfiguratorMethods aluminiumConfiguratorMethods;
	
	@Test
	void CRUDColorTests() {
		DColor newObject = crudColor.insert(Factory.createDColor());

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getCode() > 0);

		DColor updatedObject = crudColor.find(newObject.getCode());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getCode() > 0);

		updatedObject.setName("Updated Object");
		crudColor.update(updatedObject.getCode(), updatedObject);
		Assertions.assertEquals(updatedObject.getName(), crudColor.find(updatedObject.getCode()).getName());

		/*
		crudColor.delete(updatedObject.getCode());
		Assertions.assertNull(crudColor.find(updatedObject.getCode()));
		*/
	}

	@Test
	void CRUDFatherTests() {
		crudColor.insert(Factory.createDColor());

		DFather newObject = crudFather.insert(Factory.createDFather());

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getCode() > 0);

		DFather updatedObject = crudFather.find(newObject.getCode());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getCode() > 0);

		updatedObject.setDescription("Updated Object");
		crudFather.update(updatedObject.getCode(), updatedObject);
		Assertions.assertEquals(updatedObject.getDescription(), crudFather.find(updatedObject.getCode()).getDescription());

		crudFather.delete(updatedObject.getCode());
		Assertions.assertThrows(ResourceNotFoundException.class, () -> crudFather.find(updatedObject.getCode()));
	}

	@Test
	void CRUDMaterialTests() {
		DMaterial newObject = crudMaterial.insert(Factory.createDMaterial());

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getId() > 0);

		DMaterial updatedObject = crudMaterial.find(newObject.getId());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getId() > 0);

		updatedObject.setName("Updated Object");
		crudMaterial.update(updatedObject.getId(), updatedObject);
		Assertions.assertEquals(updatedObject.getName(), crudMaterial.find(updatedObject.getId()).getName());

		/*
		crudMaterial.delete(updatedObject.getCode());
		Assertions.assertNull(crudMaterial.find(updatedObject.getCode()));
		*/
	}

	@Test
	void CRUDAluminiumTypeTests() {
		DAluminiumType newObject = crudAluminiumType.insert(Factory.createDAluminiumType());

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getId() > 0);

		DAluminiumType updatedObject = crudAluminiumType.find(newObject.getId());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getId() > 0);

		updatedObject.setName("Updated Object");
		crudAluminiumType.update(updatedObject.getId(), updatedObject);
		Assertions.assertEquals(updatedObject.getName(), crudAluminiumType.find(updatedObject.getId()).getName());

		/*
		crudAluminiumType.delete(updatedObject.getCode());
		Assertions.assertNull(crudAluminiumType.find(updatedObject.getCode()));
		*/
	}

	@Test
	void CRUDAluminiumSonTests() {
		DAluminiumSon newObject = Factory.createDAluminiumSon();

		DAluminiumType newAluminiumType = crudAluminiumType.insert(Factory.createDAluminiumType());
		newObject.setAluminiumType(newAluminiumType);

		crudColor.insert(newObject.getColor());
		crudFather.insert(Factory.createDFather());

		crudAluminiumSon.insert(newObject); // debugar e ver o tipo que esta vindo

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getCode() > 0);

		DAluminiumSon updatedObject = crudAluminiumSon.find(newObject.getCode());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getCode() > 0);

		updatedObject.setDescription("Updated Object");
		crudAluminiumSon.update(updatedObject.getCode(), updatedObject);
		Assertions.assertEquals(updatedObject.getDescription(), crudAluminiumSon.find(updatedObject.getCode()).getDescription());

		crudAluminiumSon.delete(updatedObject.getCode());
		Assertions.assertThrows(ResourceNotFoundException.class, () -> crudAluminiumSon.find(updatedObject.getCode()));
	}

	@Test
	void CRUDDrawerPullTests() {
		DDrawerPull newObject = crudDrawerPull.insert(Factory.createDDrawerPull());

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getCode() > 0);

		DDrawerPull updatedObject = crudDrawerPull.find(newObject.getCode());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getCode() > 0);

		updatedObject.setDescription("Updated Object");
		crudDrawerPull.update(updatedObject.getCode(), updatedObject);
		Assertions.assertEquals(updatedObject.getDescription(), crudDrawerPull.find(updatedObject.getCode()).getDescription());

		/*
		crudDrawerPull.delete(updatedObject.getCode());
		Assertions.assertNull(crudDrawerPull.find(updatedObject.getCode()));
		*/
	}

	@Test
	void CRUDGlassTests() {
		DGlass newObject = Factory.createDGlass();
		crudColor.insert(newObject.getColor());
		
		crudGlass.insert(newObject);

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getCode() > 0);

		DGlass updatedObject = crudGlass.find(newObject.getCode());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getCode() > 0);

		updatedObject.setDescription("Updated Object");
		crudGlass.update(updatedObject.getCode(), updatedObject);
		Assertions.assertEquals(updatedObject.getDescription(), crudGlass.find(updatedObject.getCode()).getDescription());

		/*
		crudGlass.delete(updatedObject.getCode());
		Assertions.assertNull(crudGlass.find(updatedObject.getCode()));
		*/
	}

	@Test
	void CRUDMoldingTests() {
		DMolding newObject = crudMolding.insert(Factory.createDMolding());

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getCode() > 0);

		DMolding updatedObject = crudMolding.find(newObject.getCode());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getCode() > 0);

		updatedObject.setDescription("Updated Object");
		crudMolding.update(updatedObject.getCode(), updatedObject);
		Assertions.assertEquals(updatedObject.getDescription(), crudMolding.find(updatedObject.getCode()).getDescription());

		/*
		crudMolding.delete(updatedObject.getCode());
		Assertions.assertNull(crudMolding.find(updatedObject.getCode()));
		*/
	}

	@Test
	void CRUDScrewTests() {
		DScrew newObject = crudScrew.insert(Factory.createDScrew());

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getCode() > 0);

		DScrew updatedObject = crudScrew.find(newObject.getCode());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getCode() > 0);

		updatedObject.setDescription("Updated Object");
		crudScrew.update(updatedObject.getCode(), updatedObject);
		Assertions.assertEquals(updatedObject.getDescription(), crudScrew.find(updatedObject.getCode()).getDescription());

		/*
		crudScrew.delete(updatedObject.getCode());
		Assertions.assertNull(crudScrew.find(updatedObject.getCode()));
		*/
	}

	@Test
	void CRUDUsedDrawerPullTests() {
		DUsedDrawerPull newObject = Factory.createDUsedDrawerPull();

		crudColor.insert(Factory.createDColor());
		crudFather.insert(Factory.createDFather());

		DAluminiumType newAluminiumType = crudAluminiumType.insert(Factory.createDAluminiumType());
		DAluminiumSon dAluminiumSon = Factory.createDAluminiumSon();
		dAluminiumSon.setAluminiumType(newAluminiumType);
		crudAluminiumSon.insert(dAluminiumSon);
		crudDrawerPull.insert(Factory.createDDrawerPull());

		crudUsedDrawerPull.insert(newObject);

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getId() > 0);

		DUsedDrawerPull updatedObject = crudUsedDrawerPull.find(newObject.getId());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getId() > 0);

		updatedObject.setQuantity(2.0);
		crudUsedDrawerPull.update(updatedObject.getId(), updatedObject);
		Assertions.assertEquals(updatedObject.getQuantity(), crudUsedDrawerPull.find(updatedObject.getId()).getQuantity());

		crudUsedDrawerPull.delete(updatedObject.getId());
		Assertions.assertThrows(ResourceNotFoundException.class, () -> crudUsedDrawerPull.find(updatedObject.getId()));
	}

	@Test
	void CRUDUsedGlassTests() {
		DUsedGlass newObject = Factory.createDUsedGlass();

		crudColor.insert(Factory.createDColor());
		crudFather.insert(Factory.createDFather());

		DAluminiumType newAluminiumType = crudAluminiumType.insert(Factory.createDAluminiumType());
		DAluminiumSon dAluminiumSon = Factory.createDAluminiumSon();
		dAluminiumSon.setAluminiumType(newAluminiumType);
		crudAluminiumSon.insert(dAluminiumSon);
		crudGlass.insert(Factory.createDGlass());

		crudUsedGlass.insert(newObject);

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getId() > 0);

		DUsedGlass updatedObject = crudUsedGlass.find(newObject.getId());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getId() > 0);

		updatedObject.setQuantity(2.0);
		crudUsedGlass.update(updatedObject.getId(), updatedObject);
		Assertions.assertEquals(updatedObject.getQuantity(), crudUsedGlass.find(updatedObject.getId()).getQuantity());

		crudUsedGlass.delete(updatedObject.getId());
		Assertions.assertThrows(ResourceNotFoundException.class, () -> crudUsedGlass.find(updatedObject.getId()));
	}

	@Test
	void CRUDUsedMoldingTests() {
		DUsedMolding newObject = Factory.createDUsedMolding();

		crudColor.insert(Factory.createDColor());
		crudFather.insert(Factory.createDFather());

		DAluminiumType newAluminiumType = crudAluminiumType.insert(Factory.createDAluminiumType());
		DAluminiumSon dAluminiumSon = Factory.createDAluminiumSon();
		dAluminiumSon.setAluminiumType(newAluminiumType);
		crudAluminiumSon.insert(dAluminiumSon);
		crudMolding.insert(Factory.createDMolding());

		crudUsedMolding.insert(newObject);

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getId() > 0);

		DUsedMolding updatedObject = crudUsedMolding.find(newObject.getId());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getId() > 0);

		updatedObject.setQuantity(2.0);
		crudUsedMolding.update(updatedObject.getId(), updatedObject);
		Assertions.assertEquals(updatedObject.getQuantity(), crudUsedMolding.find(updatedObject.getId()).getQuantity());

		crudUsedMolding.delete(updatedObject.getId());
		Assertions.assertThrows(ResourceNotFoundException.class, () -> crudUsedMolding.find(updatedObject.getId()));
	}

	@Test
	void CRUDUsedScrewTests() {
		DUsedScrew newObject = Factory.createDUsedScrew();

		crudColor.insert(Factory.createDColor());
		crudFather.insert(Factory.createDFather());

		DAluminiumType newAluminiumType = crudAluminiumType.insert(Factory.createDAluminiumType());
		DAluminiumSon dAluminiumSon = Factory.createDAluminiumSon();
		dAluminiumSon.setAluminiumType(newAluminiumType);
		crudAluminiumSon.insert(dAluminiumSon);
		crudScrew.insert(Factory.createDScrew());

		crudUsedScrew.insert(newObject);

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getId() > 0);

		DUsedScrew updatedObject = crudUsedScrew.find(newObject.getId());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getId() > 0);

		updatedObject.setQuantity(2.0);
		crudUsedScrew.update(updatedObject.getId(), updatedObject);
		Assertions.assertEquals(updatedObject.getQuantity(), crudUsedScrew.find(updatedObject.getId()).getQuantity());

		crudUsedScrew.delete(updatedObject.getId());
		Assertions.assertThrows(ResourceNotFoundException.class, () -> crudUsedScrew.find(updatedObject.getId()));
	}

	@Test
	void CRUDUsedTrySquareTests() {
		DUsedTrySquare newObject = Factory.createDUsedTrySquare();

		crudColor.insert(Factory.createDColor());
		crudFather.insert(Factory.createDFather());

		DAluminiumType newAluminiumType = crudAluminiumType.insert(Factory.createDAluminiumType());
		DAluminiumSon dAluminiumSon = Factory.createDAluminiumSon();
		dAluminiumSon.setAluminiumType(newAluminiumType);
		crudAluminiumSon.insert(dAluminiumSon);
		crudTrySquare.insert(Factory.createDTrySquare());

		crudUsedTrySquare.insert(newObject);

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getId() > 0);

		DUsedTrySquare updatedObject = crudUsedTrySquare.find(newObject.getId());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getId() > 0);

		updatedObject.setQuantity(2.0);
		crudUsedTrySquare.update(updatedObject.getId(), updatedObject);
		Assertions.assertEquals(updatedObject.getQuantity(), crudUsedTrySquare.find(updatedObject.getId()).getQuantity());

		crudUsedTrySquare.delete(updatedObject.getId());
		Assertions.assertThrows(ResourceNotFoundException.class, () -> crudUsedTrySquare.find(updatedObject.getId()));
	}

	@Test
	void CRUDGuideTests() {
		DGuide newObject = crudGuide.insert(Factory.createDGuide());

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getId() > 0);

		DGuide updatedObject = crudGuide.find(newObject.getId());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getId() > 0);

		updatedObject.setImplementation(LocalDate.of(4000,1,1));
		crudGuide.update(updatedObject.getId(), updatedObject);
		Assertions.assertEquals(updatedObject.getImplementation(), crudGuide.find(updatedObject.getId()).getImplementation());

		crudGuide.delete(updatedObject.getId());
		Assertions.assertThrows(ResourceNotFoundException.class, () -> crudGuide.find(updatedObject.getId()));
	}

	@Test
	void CRUDMachineGroupTests() {
		DMachineGroup newObject = crudMachineGroup.insert(Factory.createDMachineGroup());

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getId() > 0);

		DMachineGroup updatedObject = crudMachineGroup.find(newObject.getId());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getId() > 0);

		updatedObject.setName("Updated name");
		crudMachineGroup.update(updatedObject.getId(), updatedObject);
		Assertions.assertEquals(updatedObject.getName(), crudMachineGroup.find(updatedObject.getId()).getName());

		/*
		crudMachineGroup.delete(updatedObject.getCode());
		Assertions.assertNull(crudMachineGroup.find(updatedObject.getCode()));
		*/
	}

	@Test
	void CRUDMachineTests() {
		DMachineGroup dMachineGroup = Factory.createDMachineGroup();
		dMachineGroup = crudMachineGroup.insert(dMachineGroup);

		DMachine newObject = Factory.createDMachine();
		newObject.setMachineGroupId(dMachineGroup.getId());

		newObject = crudMachine.insert(newObject);

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getId() > 0);

		DMachine updatedObject = crudMachine.find(newObject.getId());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getId() > 0);

		updatedObject.setName("Updated name");
		crudMachine.update(updatedObject.getId(), updatedObject);
		Assertions.assertEquals(updatedObject.getName(), crudMachine.find(updatedObject.getId()).getName());

		/*
		crudMachine.delete(updatedObject.getCode());
		Assertions.assertNull(crudMachine.find(updatedObject.getCode()));
		*/
	}

	@Test
	void CRUDGuideMachineTests() {
		DGuideMachine newObject = Factory.createDGuideMachine();

		DMachineGroup dMachineGroup = Factory.createDMachineGroup();
		dMachineGroup = crudMachineGroup.insert(dMachineGroup);

		DMachine dMachine = Factory.createDMachine();
		dMachine.setMachineGroupId(dMachineGroup.getId());
		dMachine = crudMachine.insert(dMachine);

		DGuide dGuide = Factory.createDGuide();
		dGuide = crudGuide.insert(dGuide);

		newObject.setMachineId(dMachine.getId());
		newObject.setGuideId(dGuide.getId());

		newObject = crudGuideMachine.insert(newObject);

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getId() > 0);

		DGuideMachine updatedObject = crudGuideMachine.find(newObject.getId());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getId() > 0);

		updatedObject.setManTime(10.0);
		crudGuideMachine.update(updatedObject.getId(), updatedObject);
		Assertions.assertEquals(updatedObject.getManTime(), crudGuideMachine.find(updatedObject.getId()).getManTime());

		crudGuideMachine.delete(updatedObject.getId());
		Assertions.assertThrows(ResourceNotFoundException.class, () -> crudGuideMachine.find(updatedObject.getId()));
	}

	@Test
	void CRUDBackTests() {
		DBack newObject = crudBack.insert(Factory.createDBack());

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getCode() > 0);

		DBack updatedObject = crudBack.find(newObject.getCode());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getCode() > 0);

		updatedObject.setDescription("Updated description");
		crudBack.update(updatedObject.getCode(), updatedObject);
		Assertions.assertEquals(updatedObject.getDescription(), crudBack.find(updatedObject.getCode()).getDescription());

		crudBack.delete(updatedObject.getCode());
		Assertions.assertThrows(ResourceNotFoundException.class, () -> crudBack.find(updatedObject.getCode()));
	}

	@Test
	void CRUDPaintingTypeTests() {
		DPaintingType newObject = Factory.createDPaintingType();
		newObject =	crudPaintingType.insert(newObject);

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getId() > 0);

		DPaintingType updatedObject = crudPaintingType.find(newObject.getId());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getId() > 0);

		updatedObject.setDescription("Updated description");
		crudPaintingType.update(updatedObject.getId(), updatedObject);
		Assertions.assertEquals(updatedObject.getDescription(), crudPaintingType.find(updatedObject.getId()).getDescription());

		/*
		crudPaintingType.delete(updatedObject.getCode());
		Assertions.assertNull(crudPaintingType.find(updatedObject.getCode()));
		*/
	}

	@Test
	void CRUDPaintingTests() {
		DColor color = Factory.createDColor();
		color = crudColor.insert(color);
		DPaintingType paintingType = Factory.createDPaintingType();
		paintingType = crudPaintingType.insert(paintingType);

		DPainting newObject = Factory.createDPainting();
		newObject.setPaintingType(paintingType);
		newObject.setColor(color);
		newObject =	crudPainting.insert(newObject);

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getCode() > 0);

		DPainting updatedObject = crudPainting.find(newObject.getCode());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getCode() > 0);

		updatedObject.setDescription("Updated description");
		crudPainting.update(updatedObject.getCode(), updatedObject);
		Assertions.assertEquals(updatedObject.getDescription(), crudPainting.find(updatedObject.getCode()).getDescription());

		/*
		crudPainting.delete(updatedObject.getCode());
		Assertions.assertNull(crudPainting.find(updatedObject.getCode()));
		*/
	}

	@Test
	void CRUDPaintingBorderBackgroundTests() {
		DPaintingBorderBackground newObject = crudPaintingBorderBackground.insert(Factory.createDPaintingBorderBackground());

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getCode() > 0);

		DPaintingBorderBackground updatedObject = crudPaintingBorderBackground.find(newObject.getCode());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getCode() > 0);

		updatedObject.setDescription("Updated description");
		crudPaintingBorderBackground.update(updatedObject.getCode(), updatedObject);
		Assertions.assertEquals(updatedObject.getDescription(), crudPaintingBorderBackground.find(updatedObject.getCode()).getDescription());

		/*
		crudPaintingBorderBackground.delete(updatedObject.getCode());
		Assertions.assertNull(crudPaintingBorderBackground.find(updatedObject.getCode()));
		*/
	}

	@Test
	void CRUDPolyesterTests() {
		DPolyester newObject = crudPolyester.insert(Factory.createDPolyester());

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getCode() > 0);

		DPolyester updatedObject = crudPolyester.find(newObject.getCode());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getCode() > 0);

		updatedObject.setDescription("Updated description");
		crudPolyester.update(updatedObject.getCode(), updatedObject);
		Assertions.assertEquals(updatedObject.getDescription(), crudPolyester.find(updatedObject.getCode()).getDescription());

		/*
		crudPolyester.delete(updatedObject.getCode());
		Assertions.assertNull(crudPolyester.find(updatedObject.getCode()));
		*/
	}

	@Test
	void CRUDPaintingSonTests() {
		DPaintingSon newObject = Factory.createDPaintingSon();

		DColor color = crudColor.insert(Factory.createDColor());
		DFather father = crudFather.insert(Factory.createDFather());

		newObject.setColor(color);
		newObject.setFatherCode(father.getCode());

		crudPaintingSon.insert(newObject);

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getCode() > 0);

		DPaintingSon updatedObject = crudPaintingSon.find(newObject.getCode());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getCode() > 0);

		updatedObject.setDescription("Updated Object");
		crudPaintingSon.update(updatedObject.getCode(), updatedObject);
		Assertions.assertEquals(updatedObject.getDescription(), crudPaintingSon.find(updatedObject.getCode()).getDescription());

		crudPaintingSon.delete(updatedObject.getCode());
		Assertions.assertThrows(ResourceNotFoundException.class, () -> crudPaintingSon.find(updatedObject.getCode()));
	}

	@Test
	void CRUDUsedBackSheetTests() {
		DUsedBackSheet newObject = Factory.createDUsedBackSheet();

		DMaterial material = Factory.createDMaterial();
		material = crudMaterial.insert(material);
		DColor color = crudColor.insert(Factory.createDColor());

		DSheet sheet = Factory.createDSheet();
		sheet.setMaterialId(material.getId());
		sheet.setColor(color);
		sheet = crudSheet.insert(sheet);
		DBack back = crudBack.insert(Factory.createDBack());

		newObject.setBackCode(back.getCode());
		newObject.setSheetCode(sheet.getCode());
		newObject = crudUsedBackSheet.insert(newObject);

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getId() > 0);

		DUsedBackSheet updatedObject = crudUsedBackSheet.find(newObject.getId());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getId() > 0);

		updatedObject.setNetQuantity(2.0);
		crudUsedBackSheet.update(updatedObject.getId(), updatedObject);
		Assertions.assertEquals(updatedObject.getNetQuantity(), crudUsedBackSheet.find(updatedObject.getId()).getNetQuantity());

		crudUsedBackSheet.delete(updatedObject.getId());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> crudUsedBackSheet.find(updatedObject.getId()));
	}

	@Test
	void CRUDUsedPaintingTests() {
		DUsedPainting newObject = Factory.createDUsedPainting();

		DPaintingSon paintingSon = Factory.createDPaintingSon();
		DColor color = crudColor.insert(Factory.createDColor());
		DFather father = crudFather.insert(Factory.createDFather());

		paintingSon.setColor(color);
		paintingSon.setFatherCode(father.getCode());
		paintingSon = crudPaintingSon.insert(paintingSon);

		DPaintingType paintingType = Factory.createDPaintingType();
		paintingType = crudPaintingType.insert(paintingType);

		DPainting painting = Factory.createDPainting();
		painting.setPaintingType(paintingType);
		painting.setColor(color);
		painting = crudPainting.insert(painting);

		newObject.setPaintingSonCode(paintingSon.getCode());
		newObject.setPaintingCode(painting.getCode());

		newObject = crudUsedPainting.insert(newObject);

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getId() > 0);

		DUsedPainting updatedObject = crudUsedPainting.find(newObject.getId());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getId() > 0);

		updatedObject.setNetQuantity(2.0);
		crudUsedPainting.update(updatedObject.getId(), updatedObject);
		Assertions.assertEquals(updatedObject.getNetQuantity(), crudUsedPainting.find(updatedObject.getId()).getNetQuantity());

		crudUsedPainting.delete(updatedObject.getId());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> crudUsedPainting.find(updatedObject.getId()));
	}

	@Test
	void CRUDUsedPaintingBorderBackgroundTests() {
		DUsedPaintingBorderBackground newObject = Factory.createDUsedPaintingBorderBackground();

		DPaintingSon paintingSon = Factory.createDPaintingSon();
		DColor color = crudColor.insert(Factory.createDColor());
		DFather father = crudFather.insert(Factory.createDFather());

		paintingSon.setColor(color);
		paintingSon.setFatherCode(father.getCode());
		paintingSon = crudPaintingSon.insert(paintingSon);

		DPaintingBorderBackground paintingBorderBackground = Factory.createDPaintingBorderBackground();
		paintingBorderBackground = crudPaintingBorderBackground.insert(paintingBorderBackground);

		newObject.setPaintingSonCode(paintingSon.getCode());
		newObject.setPaintingBorderBackgroundCode(paintingBorderBackground.getCode());

		newObject = crudUsedPaintingBorderBackground.insert(newObject);

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getId() > 0);

		DUsedPaintingBorderBackground updatedObject = crudUsedPaintingBorderBackground.find(newObject.getId());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getId() > 0);

		updatedObject.setNetQuantity(2.0);
		crudUsedPaintingBorderBackground.update(updatedObject.getId(), updatedObject);
		Assertions.assertEquals(updatedObject.getNetQuantity(), crudUsedPaintingBorderBackground.find(updatedObject.getId()).getNetQuantity());

		crudUsedPaintingBorderBackground.delete(updatedObject.getId());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> crudUsedPaintingBorderBackground.find(updatedObject.getId()));
	}

	@Test
	void CRUDUsedPolyesterTests() {
		DUsedPolyester newObject = Factory.createDUsedPolyester();

		DPaintingSon paintingSon = Factory.createDPaintingSon();
		DColor color = crudColor.insert(Factory.createDColor());
		DFather father = crudFather.insert(Factory.createDFather());

		paintingSon.setColor(color);
		paintingSon.setFatherCode(father.getCode());
		paintingSon = crudPaintingSon.insert(paintingSon);

		DPolyester Polyester = Factory.createDPolyester();
		Polyester = crudPolyester.insert(Polyester);

		newObject.setPaintingSonCode(paintingSon.getCode());
		newObject.setPolyesterCode(Polyester.getCode());

		newObject = crudUsedPolyester.insert(newObject);

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getId() > 0);

		DUsedPolyester updatedObject = crudUsedPolyester.find(newObject.getId());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getId() > 0);

		updatedObject.setNetQuantity(2.0);
		crudUsedPolyester.update(updatedObject.getId(), updatedObject);
		Assertions.assertEquals(updatedObject.getNetQuantity(), crudUsedPolyester.find(updatedObject.getId()).getNetQuantity());

		crudUsedPolyester.delete(updatedObject.getId());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> crudUsedPolyester.find(updatedObject.getId()));
	}

	@Test
	void CRUDEdgeBandingTests() {
		DEdgeBanding newObject = Factory.createDEdgeBanding();
		crudColor.insert(Factory.createDColor());

		newObject = crudEdgeBanding.insert(newObject);

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getCode() > 0);

		DEdgeBanding updatedObject = crudEdgeBanding.find(newObject.getCode());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getCode() > 0);

		updatedObject.setDescription("Updated description");
		crudEdgeBanding.update(updatedObject.getCode(), updatedObject);
		Assertions.assertEquals(updatedObject.getDescription(), crudEdgeBanding.find(updatedObject.getCode()).getDescription());

		/*
		crudEdgeBanding.delete(updatedObject.getCode());
		Assertions.assertNull(crudEdgeBanding.find(updatedObject.getCode()));
		*/
	}

	@Test
	void CRUDGlueTests() {
		DGlue newObject = Factory.createDGlue();

		newObject = crudGlue.insert(newObject);

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getCode() > 0);

		DGlue updatedObject = crudGlue.find(newObject.getCode());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getCode() > 0);

		updatedObject.setDescription("Updated description");
		crudGlue.update(updatedObject.getCode(), updatedObject);
		Assertions.assertEquals(updatedObject.getDescription(), crudGlue.find(updatedObject.getCode()).getDescription());

		/*
		crudGlue.delete(updatedObject.getCode());
		Assertions.assertNull(crudGlue.find(updatedObject.getCode()));
		*/
	}

	@Test
	void CRUDSheetTests() {
		DSheet newObject = Factory.createDSheet();

		crudColor.insert(Factory.createDColor());
		DMaterial material = Factory.createDMaterial();
		material = crudMaterial.insert(material);
		newObject.setMaterialId(material.getId());

		newObject = crudSheet.insert(newObject);

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getCode() > 0);

		DSheet updatedObject = crudSheet.find(newObject.getCode());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getCode() > 0);

		updatedObject.setDescription("Updated description");
		crudSheet.update(updatedObject.getCode(), updatedObject);
		Assertions.assertEquals(updatedObject.getDescription(), crudSheet.find(updatedObject.getCode()).getDescription());

		/*
		crudSheet.delete(updatedObject.getCode());
		Assertions.assertNull(crudSheet.find(updatedObject.getCode()));
		*/
	}

	@Test
	void CRUDMDPSonTests() {
		DMDPSon newObject = Factory.createDMDPSon();

		DColor color = crudColor.insert(Factory.createDColor());
		DFather father = crudFather.insert(Factory.createDFather());

		newObject.setColor(color);
		newObject.setFatherCode(father.getCode());

		newObject = crudMDPSon.insert(newObject);

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getCode() > 0);

		DMDPSon updatedObject = crudMDPSon.find(newObject.getCode());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getCode() > 0);

		updatedObject.setDescription("Updated Object");
		crudMDPSon.update(updatedObject.getCode(), updatedObject);
		Assertions.assertEquals(updatedObject.getDescription(), crudMDPSon.find(updatedObject.getCode()).getDescription());

		crudMDPSon.delete(updatedObject.getCode());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> crudMDPSon.find(updatedObject.getCode()));
	}

	@Test
	void CRUDUsedEdgeBandingTests() {
		DUsedEdgeBanding newObject = Factory.createDUsedEdgeBanding();

		DMDPSon MDPSon = Factory.createDMDPSon();
		DColor color = crudColor.insert(Factory.createDColor());
		DFather father = crudFather.insert(Factory.createDFather());

		MDPSon.setColor(color);
		MDPSon.setFatherCode(father.getCode());
		MDPSon = crudMDPSon.insert(MDPSon);

		DEdgeBanding EdgeBanding = Factory.createDEdgeBanding();
		EdgeBanding = crudEdgeBanding.insert(EdgeBanding);

		newObject.setMdpSonCode(MDPSon.getCode());
		newObject.setEdgeBandingCode(EdgeBanding.getCode());

		newObject = crudUsedEdgeBanding.insert(newObject);

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getId() > 0);

		DUsedEdgeBanding updatedObject = crudUsedEdgeBanding.find(newObject.getId());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getId() > 0);

		updatedObject.setNetQuantity(2.0);
		crudUsedEdgeBanding.update(updatedObject.getId(), updatedObject);
		Assertions.assertEquals(updatedObject.getNetQuantity(), crudUsedEdgeBanding.find(updatedObject.getId()).getNetQuantity());

		crudUsedEdgeBanding.delete(updatedObject.getId());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> crudUsedEdgeBanding.find(updatedObject.getId()));
	}

	@Test
	void CRUDUsedGlueTests() {
		DUsedGlue newObject = Factory.createDUsedGlue();

		DMDPSon MDPSon = Factory.createDMDPSon();
		DColor color = crudColor.insert(Factory.createDColor());
		DFather father = crudFather.insert(Factory.createDFather());

		MDPSon.setColor(color);
		MDPSon.setFatherCode(father.getCode());
		MDPSon = crudMDPSon.insert(MDPSon);

		DGlue Glue = Factory.createDGlue();
		Glue = crudGlue.insert(Glue);

		newObject.setMdpSonCode(MDPSon.getCode());
		newObject.setGlueCode(Glue.getCode());

		newObject = crudUsedGlue.insert(newObject);

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getId() > 0);

		DUsedGlue updatedObject = crudUsedGlue.find(newObject.getId());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getId() > 0);

		updatedObject.setNetQuantity(2.0);
		crudUsedGlue.update(updatedObject.getId(), updatedObject);
		Assertions.assertEquals(updatedObject.getNetQuantity(), crudUsedGlue.find(updatedObject.getId()).getNetQuantity());

		crudUsedGlue.delete(updatedObject.getId());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> crudUsedGlue.find(updatedObject.getId()));
	}

	@Test
	void CRUDUsedSheetTests() {
		DUsedSheet newObject = Factory.createDUsedSheet();

		DMDPSon MDPSon = Factory.createDMDPSon();
		DColor color = crudColor.insert(Factory.createDColor());
		DFather father = crudFather.insert(Factory.createDFather());

		MDPSon.setColor(color);
		MDPSon.setFatherCode(father.getCode());
		MDPSon = crudMDPSon.insert(MDPSon);

		DSheet Sheet = Factory.createDSheet();
		Sheet.setColor(color);
		DMaterial material = Factory.createDMaterial();
		material = crudMaterial.insert(material);
		Sheet.setMaterialId(material.getId());

		Sheet = crudSheet.insert(Sheet);

		newObject.setMdpSonCode(MDPSon.getCode());
		newObject.setSheetCode(Sheet.getCode());

		newObject = crudUsedSheet.insert(newObject);

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getId() > 0);

		DUsedSheet updatedObject = crudUsedSheet.find(newObject.getId());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getId() > 0);

		updatedObject.setNetQuantity(2.0);
		crudUsedSheet.update(updatedObject.getId(), updatedObject);
		Assertions.assertEquals(updatedObject.getNetQuantity(), crudUsedSheet.find(updatedObject.getId()).getNetQuantity());

		crudUsedSheet.delete(updatedObject.getId());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> crudUsedSheet.find(updatedObject.getId()));
	}

	@Test
	void CRUDCornerBracketTests() {
		DCornerBracket newObject = Factory.createDCornerBracket();

		newObject = crudCornerBracket.insert(newObject);

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getCode() > 0);

		DCornerBracket updatedObject = crudCornerBracket.find(newObject.getCode());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getCode() > 0);

		updatedObject.setDescription("Updated description");
		crudCornerBracket.update(updatedObject.getCode(), updatedObject);
		Assertions.assertEquals(updatedObject.getDescription(), crudCornerBracket.find(updatedObject.getCode()).getDescription());

		/*
		crudCornerBracket.delete(updatedObject.getCode());
		Assertions.assertNull(crudCornerBracket.find(updatedObject.getCode()));
		*/
	}

	@Test
	void CRUDNonwovenFabricTests() {
		DNonwovenFabric newObject = Factory.createDNonwovenFabric();

		newObject = crudNonwovenFabric.insert(newObject);

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getCode() > 0);

		DNonwovenFabric updatedObject = crudNonwovenFabric.find(newObject.getCode());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getCode() > 0);

		updatedObject.setDescription("Updated description");
		crudNonwovenFabric.update(updatedObject.getCode(), updatedObject);
		Assertions.assertEquals(updatedObject.getDescription(), crudNonwovenFabric.find(updatedObject.getCode()).getDescription());

		/*
		crudNonwovenFabric.delete(updatedObject.getCode());
		Assertions.assertNull(crudNonwovenFabric.find(updatedObject.getCode()));
		*/
	}

	@Test
	void CRUDPlasticTests() {
		DPlastic newObject = Factory.createDPlastic();

		newObject = crudPlastic.insert(newObject);

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getCode() > 0);

		DPlastic updatedObject = crudPlastic.find(newObject.getCode());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getCode() > 0);

		updatedObject.setDescription("Updated description");
		crudPlastic.update(updatedObject.getCode(), updatedObject);
		Assertions.assertEquals(updatedObject.getDescription(), crudPlastic.find(updatedObject.getCode()).getDescription());

		/*
		crudPlastic.delete(updatedObject.getCode());
		Assertions.assertNull(crudPlastic.find(updatedObject.getCode()));
		*/
	}

	@Test
	void CRUDPolyethyleneTests() {
		DPolyethylene newObject = Factory.createDPolyethylene();

		newObject = crudPolyethylene.insert(newObject);

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getCode() > 0);

		DPolyethylene updatedObject = crudPolyethylene.find(newObject.getCode());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getCode() > 0);

		updatedObject.setDescription("Updated description");
		crudPolyethylene.update(updatedObject.getCode(), updatedObject);
		Assertions.assertEquals(updatedObject.getDescription(), crudPolyethylene.find(updatedObject.getCode()).getDescription());

		/*
		crudPolyethylene.delete(updatedObject.getCode());
		Assertions.assertNull(crudPolyethylene.find(updatedObject.getCode()));
		*/
	}

	@Test
	void CRUDGhostTests() {
		DGhost newObject = Factory.createDGhost();

		newObject = crudGhost.insert(newObject);

		Assertions.assertNotNull(newObject);

		DGhost updatedObject = crudGhost.find(newObject.getCode());
		Assertions.assertNotNull(updatedObject);

		updatedObject.setDescription("Updated description");
		crudGhost.update(updatedObject.getCode(), updatedObject);
		Assertions.assertEquals(updatedObject.getDescription(), crudGhost.find(updatedObject.getCode()).getDescription());

		crudGhost.delete(updatedObject.getCode());
		Assertions.assertThrows(ResourceNotFoundException.class, () -> crudGhost.find(updatedObject.getCode()));
	}

	@Test
	void CRUDUsedCornerBracketTests() {
		DUsedCornerBracket newObject = Factory.createDUsedCornerBracket();

		DGhost Ghost = Factory.createDGhost();
		Ghost = crudGhost.insert(Ghost);

		DCornerBracket CornerBracket = Factory.createDCornerBracket();
		CornerBracket = crudCornerBracket.insert(CornerBracket);

		newObject.setGhostCode(Ghost.getCode());
		newObject.setCornerBracketCode(CornerBracket.getCode());

		newObject = crudUsedCornerBracket.insert(newObject);

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getId() > 0);

		DUsedCornerBracket updatedObject = crudUsedCornerBracket.find(newObject.getId());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getId() > 0);

		updatedObject.setNetQuantity(2.0);
		crudUsedCornerBracket.update(updatedObject.getId(), updatedObject);
		Assertions.assertEquals(updatedObject.getNetQuantity(), crudUsedCornerBracket.find(updatedObject.getId()).getNetQuantity());

		crudUsedCornerBracket.delete(updatedObject.getId());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> crudUsedCornerBracket.find(updatedObject.getId()));
	}

	@Test
	void CRUDUsedNonwovenFabricTests() {
		DUsedNonwovenFabric newObject = Factory.createDUsedNonwovenFabric();

		DGhost Ghost = Factory.createDGhost();
		Ghost = crudGhost.insert(Ghost);

		DNonwovenFabric NonwovenFabric = Factory.createDNonwovenFabric();
		NonwovenFabric = crudNonwovenFabric.insert(NonwovenFabric);

		newObject.setGhostCode(Ghost.getCode());
		newObject.setNonwovenFabricCode(NonwovenFabric.getCode());

		newObject = crudUsedNonwovenFabric.insert(newObject);

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getId() > 0);

		DUsedNonwovenFabric updatedObject = crudUsedNonwovenFabric.find(newObject.getId());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getId() > 0);

		updatedObject.setNetQuantity(2.0);
		crudUsedNonwovenFabric.update(updatedObject.getId(), updatedObject);
		Assertions.assertEquals(updatedObject.getNetQuantity(), crudUsedNonwovenFabric.find(updatedObject.getId()).getNetQuantity());

		crudUsedNonwovenFabric.delete(updatedObject.getId());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> crudUsedNonwovenFabric.find(updatedObject.getId()));
	}

	@Test
	void CRUDUsedPlasticTests() {
		DUsedPlastic newObject = Factory.createDUsedPlastic();

		DGhost Ghost = Factory.createDGhost();
		Ghost = crudGhost.insert(Ghost);

		DPlastic Plastic = Factory.createDPlastic();
		Plastic = crudPlastic.insert(Plastic);

		newObject.setGhostCode(Ghost.getCode());
		newObject.setPlasticCode(Plastic.getCode());

		newObject = crudUsedPlastic.insert(newObject);

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getId() > 0);

		DUsedPlastic updatedObject = crudUsedPlastic.find(newObject.getId());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getId() > 0);

		updatedObject.setNetQuantity(2.0);
		crudUsedPlastic.update(updatedObject.getId(), updatedObject);
		Assertions.assertEquals(updatedObject.getNetQuantity(), crudUsedPlastic.find(updatedObject.getId()).getNetQuantity());

		crudUsedPlastic.delete(updatedObject.getId());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> crudUsedPlastic.find(updatedObject.getId()));
	}

	@Test
	void CRUDUsedPolyethyleneTests() {
		DUsedPolyethylene newObject = Factory.createDUsedPolyethylene();

		DGhost Ghost = Factory.createDGhost();
		Ghost = crudGhost.insert(Ghost);

		DPolyethylene Polyethylene = Factory.createDPolyethylene();
		Polyethylene = crudPolyethylene.insert(Polyethylene);

		newObject.setGhostCode(Ghost.getCode());
		newObject.setPolyethyleneCode(Polyethylene.getCode());

		newObject = crudUsedPolyethylene.insert(newObject);

		Assertions.assertNotNull(newObject);
		Assertions.assertTrue(newObject.getId() > 0);

		DUsedPolyethylene updatedObject = crudUsedPolyethylene.find(newObject.getId());
		Assertions.assertNotNull(updatedObject);
		Assertions.assertTrue(updatedObject.getId() > 0);

		updatedObject.setNetQuantity(2.0);
		crudUsedPolyethylene.update(updatedObject.getId(), updatedObject);
		Assertions.assertEquals(updatedObject.getNetQuantity(), crudUsedPolyethylene.find(updatedObject.getId()).getNetQuantity());

		crudUsedPolyethylene.delete(updatedObject.getId());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> crudUsedPolyethylene.find(updatedObject.getId()));
	}

	@Test
	void FatherGeneratorMethodsTests() {
		Father father = (Father) fatherGeneratorMethods.createOrUpdateFather(Factory.createDFatherGenerator());
		Assertions.assertNotNull(father);
		Assertions.assertNotNull(father.getMeasure1());

		DDrawerPull drawerPull = crudDrawerPull.insert(Factory.createDDrawerPull());
		crudColor.insert(Factory.createDColor());

		fatherGeneratorMethods.addAttachment(drawerPull.getCode(), father);
		Assertions.assertNotNull(father.getAttachments());
	}

	@Test
	void GhostGeneratorMethodsTests() {
		Father father = (Father) fatherGeneratorMethods.createOrUpdateFather(Factory.createDFatherGenerator());
		Assertions.assertNotNull(father);
		Assertions.assertNotNull(father.getMeasure1());

		ghostGeneratorMethods.generateGhost("F99", father);
		Assertions.assertNotNull(father.getGhost());

		// struct

		crudCornerBracket.insert(Factory.createDCornerBracket());
		crudPlastic.insert(Factory.createDPlastic());
		crudNonwovenFabric.insert(Factory.createDNonwovenFabric());
		crudPolyethylene.insert(Factory.createDPolyethylene());

		ghostGeneratorMethods.generateGhostStruct(Factory.createDGhostGenerator());
	}

	@Test
	void GuideGeneratorMethodsTests() {
		DMachineGroup dMachineGroup = Factory.createDMachineGroup();
		dMachineGroup = crudMachineGroup.insert(dMachineGroup);

		DMachine machine = Factory.createDMachine();
		machine.setMachineGroupId(dMachineGroup.getId());

		machine = crudMachine.insert(machine);

		DSon son = Factory.createDSon();

		DColor color = crudColor.insert(Factory.createDColor());
		DFather father = crudFather.insert(Factory.createDFather());

		son.setColor(color);
		son.setFatherCode(father.getCode());

		crudSon.insert(son);

		DGuideGenerator guideGenerator = Factory.createDGuideGenerator();
		guideGenerator.setMachinesIds(List.of(machine.getId()));
		guideGeneratorMethods.generateGuideSon(guideGenerator);
	}

	/*
	@Test
	void AluminiumConfiguratorMethodsTests() {
		crudCornerBracket.insert(Factory.createDCornerBracket());
		crudPlastic.insert(Factory.createDPlastic());
		crudNonwovenFabric.insert(Factory.createDNonwovenFabric());
		crudPolyethylene.insert(Factory.createDPolyethylene());

		crudColor.insert(Factory.createDColor());
		crudScrew.insert(Factory.createDScrew());

		DAluminiumType aluminiumType = crudAluminiumType.insert(Factory.createDAluminiumType());
		crudDrawerPull.insert(Factory.createDDrawerPull());
		crudTrySquare.insert(Factory.createDTrySquare());
		crudMolding.insert(Factory.createDMolding());
		crudGlue.insert(Factory.createDGlue());

		crudFather.insert(Factory.createDFather());
		DAluminiumSon dAluminiumSon = Factory.createDAluminiumSon();
		dAluminiumSon.setAluminiumType(aluminiumType);
		crudAluminiumSon.insert(dAluminiumSon);

		DAluminiumConfigurator aluminiumConfigurator = Factory.createDAluminiumConfigurator();
		aluminiumConfigurator.setAluminiumTypeId(aluminiumType.getId());

		List<DFather> list = aluminiumConfiguratorMethods.generateStruct(aluminiumConfigurator);

		Assertions.assertNotNull(list);
	}

	 */
}
