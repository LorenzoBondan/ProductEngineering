package br.com.todeschini.persistence.configurator.generators.songenerator;

import br.com.todeschini.domain.business.configurator.generators.guidegenerator.DGuideGenerator;
import br.com.todeschini.domain.business.configurator.generators.guidegenerator.api.GuideGeneratorService;
import br.com.todeschini.domain.business.configurator.generators.songenerator.DSonGenerator;
import br.com.todeschini.domain.business.configurator.generators.songenerator.spi.SonGeneratorMethods;
import br.com.todeschini.domain.configurator.SonConfigurator;
import br.com.todeschini.persistence.aluminium.aluminiumson.AluminiumSonRepository;
import br.com.todeschini.persistence.entities.aluminium.AluminiumSon;
import br.com.todeschini.persistence.entities.mdf.PaintingSon;
import br.com.todeschini.persistence.entities.mdp.MDPSon;
import br.com.todeschini.persistence.entities.publico.Color;
import br.com.todeschini.persistence.entities.publico.Father;
import br.com.todeschini.persistence.mdf.paintingson.PaintingSonRepository;
import br.com.todeschini.persistence.mdp.mdpson.MDPSonRepository;
import br.com.todeschini.persistence.publico.father.FatherRepository;
import br.com.todeschini.persistence.util.CustomUserUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class SonGeneratorMethodsImpl implements SonGeneratorMethods {

    private final MDPSonRepository mdpSonRepository;
    private final PaintingSonRepository paintingSonRepository;
    private final FatherRepository fatherRepository;
    private final AluminiumSonRepository aluminiumSonRepository;
    private final GuideGeneratorService guideGeneratorService;
    private final CustomUserUtil customUserUtil;

    public SonGeneratorMethodsImpl(MDPSonRepository mdpSonRepository, PaintingSonRepository paintingSonRepository,
                                   AluminiumSonRepository aluminiumSonRepository, FatherRepository fatherRepository,
                                   GuideGeneratorService guideGeneratorService, CustomUserUtil customUserUtil) {
        this.mdpSonRepository = mdpSonRepository;
        this.paintingSonRepository = paintingSonRepository;
        this.fatherRepository = fatherRepository;
        this.aluminiumSonRepository = aluminiumSonRepository;
        this.guideGeneratorService = guideGeneratorService;
        this.customUserUtil = customUserUtil;
    }

    @Override
    @Transactional
    public Object createOrUpdateMDPSon(DSonGenerator sonGenerator) {
        MDPSon son = mdpSonRepository.existsById(sonGenerator.getSonCode()) ? mdpSonRepository.findById(sonGenerator.getSonCode()).get() : new MDPSon();
        if (!mdpSonRepository.existsById(sonGenerator.getSonCode())) {
            son.setDescription((sonGenerator.getSonDescription() + " - " + ((Color) sonGenerator.getColor()).getName()).trim().toUpperCase());
            son.setCode(sonGenerator.getSonCode());
            son.setMeasurements();
            son.setColor((Color) sonGenerator.getColor());
        }
        if(sonGenerator.getAluminiumSon() != null){
            saveOrUpdateSonAndAluminiumSon(son, ((AluminiumSon) sonGenerator.getAluminiumSon()));
        } else if (sonGenerator.getFather() != null){
            saveOrUpdateSonAndFather(son, ((Father) sonGenerator.getFather()));
        }
        return son;
    }

    @Override
    @Transactional
    public Object createOrUpdatePaintingSon(DSonGenerator sonGenerator) {
        PaintingSon son = paintingSonRepository.existsById(sonGenerator.getSonCode()) ? paintingSonRepository.findById(sonGenerator.getSonCode()).get() : new PaintingSon();
        if (!paintingSonRepository.existsById(sonGenerator.getSonCode())) {
            son.setDescription((sonGenerator.getSonDescription() + " - " + ((Color) sonGenerator.getColor()).getName()).trim().toUpperCase());
            son.setCode(sonGenerator.getSonCode());
            son.setMeasurements();
            son.setColor((Color) sonGenerator.getColor());
        }
        saveOrUpdatePaintingSonAndFather(son, (Father) sonGenerator.getFather());
        return son;
    }

    @Override
    @Transactional
    public Object createOrUpdateAluminiumSon(DSonGenerator sonGenerator) {
        Long aluminiumSonCode = Long.parseLong(sonGenerator.getSonCode().toString() + ((Color) sonGenerator.getColor()).getCode().toString());
        AluminiumSon aluminiumSon = aluminiumSonRepository.existsById(aluminiumSonCode) ? aluminiumSonRepository.findById(aluminiumSonCode).get() : new AluminiumSon();
        if (!aluminiumSonRepository.existsById(aluminiumSonCode)) {
            aluminiumSon.setDescription((sonGenerator.getSonDescription() + " - " + ((Color) sonGenerator.getColor()).getName()).trim().toUpperCase());
            aluminiumSon.setCode(aluminiumSonCode);
            aluminiumSon.setColor(((Color) sonGenerator.getColor()));
            aluminiumSon.setFather((Father) sonGenerator.getFather());
            aluminiumSon.setMeasurements();
        }
        saveOrUpdateFatherAndAluminiumSon((Father) sonGenerator.getFather(), aluminiumSon);
        return aluminiumSon;
    }

    private void saveOrUpdateSonAndFather(MDPSon son, Father father) {
        setFatherCreationData(father);
        setMDPSonCreationData(son);
        mdpSonRepository.save(son);
        father.getSons().add(son);
        fatherRepository.save(father);
        son.setFather(father);
        mdpSonRepository.save(son);
    }

    private void saveOrUpdatePaintingSonAndFather(PaintingSon son, Father father) {
        setFatherCreationData(father);
        setPaintingSonCreationData(son);
        son.setImplementation(father.getImplementation());
        paintingSonRepository.save(son);
        father.getSons().add(son);
        fatherRepository.save(father);
        son.setFather(father);
        paintingSonRepository.save(son);
    }

    private void saveOrUpdateSonAndAluminiumSon(MDPSon son, AluminiumSon aluminiumSon) {
        setMDPSonCreationData(son);
        setAluminiumSonCreationData(aluminiumSon);
        son.setImplementation(aluminiumSon.getImplementation());
        mdpSonRepository.save(son);
        aluminiumSon.getSons().add(son);
        aluminiumSonRepository.save(aluminiumSon);
        son.getAluminiumSons().add(aluminiumSon);
        mdpSonRepository.save(son);
    }

    private void saveOrUpdateFatherAndAluminiumSon(Father father, AluminiumSon aluminiumSon) {
        setFatherCreationData(father);
        setAluminiumSonCreationData(aluminiumSon);
        aluminiumSon.setImplementation(father.getImplementation());
        fatherRepository.save(father);
        aluminiumSon.setFather(father);
        aluminiumSonRepository.save(aluminiumSon);
        father.getSons().add(aluminiumSon);
        fatherRepository.save(father);
    }

    @Override
    @Transactional
    public void generateOrUpdateSonsAndGuides(List<Object> sonConfiguratorList, Object color, Object father, Object aluminiumSon, LocalDate implementation) {
        for (Object configuratorObj : sonConfiguratorList) {
            if (configuratorObj instanceof SonConfigurator configurator) {
                MDPSon son = (MDPSon) createOrUpdateMDPSon(new DSonGenerator(Long.parseLong(configurator.getCode().toString() + ((Color) color).getCode().toString()),
                        configurator.getDescription(), color, father, aluminiumSon));
                guideGeneratorService.generateGuideSon(new DGuideGenerator(son, configurator.getMachinesIds(), implementation, LocalDate.parse("9999-12-31")));
            }
        }
    }

    private void setFatherCreationData(Father father){
        if(father.getCreatedBy() == null){
            father.setCreatedBy(customUserUtil.getLoggedUsername());
            father.setCreationDate(LocalDateTime.now());
        }
    }

    private void setMDPSonCreationData(MDPSon son){
        if(son.getCreatedBy() == null){
            son.setCreatedBy(customUserUtil.getLoggedUsername());
            son.setCreationDate(LocalDateTime.now());
        }
    }

    private void setPaintingSonCreationData(PaintingSon son){
        if(son.getCreatedBy() == null){
            son.setCreatedBy(customUserUtil.getLoggedUsername());
            son.setCreationDate(LocalDateTime.now());
        }
    }

    private void setAluminiumSonCreationData(AluminiumSon son){
        if(son.getCreatedBy() == null){
            son.setCreatedBy(customUserUtil.getLoggedUsername());
            son.setCreationDate(LocalDateTime.now());
        }
    }
}
