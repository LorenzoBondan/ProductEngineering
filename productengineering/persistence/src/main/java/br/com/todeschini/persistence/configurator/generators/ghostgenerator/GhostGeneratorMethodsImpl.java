package br.com.todeschini.persistence.configurator.generators.ghostgenerator;

import br.com.todeschini.domain.business.configurator.generators.ghostgenerator.DGhostGenerator;
import br.com.todeschini.domain.business.configurator.generators.ghostgenerator.spi.GhostGeneratorMethods;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.packaging.*;
import br.com.todeschini.persistence.entities.publico.Father;
import br.com.todeschini.persistence.packaging.cornerbracket.CornerBracketRepository;
import br.com.todeschini.persistence.packaging.ghost.GhostRepository;
import br.com.todeschini.persistence.packaging.nonwovenfabric.NonwovenFabricRepository;
import br.com.todeschini.persistence.packaging.plastic.PlasticRepository;
import br.com.todeschini.persistence.packaging.polyethylene.PolyethyleneRepository;
import br.com.todeschini.persistence.packaging.usedcornerbracket.UsedCornerBracketRepository;
import br.com.todeschini.persistence.packaging.usednonwovenfabric.UsedNonwovenFabricRepository;
import br.com.todeschini.persistence.packaging.usedplastic.UsedPlasticRepository;
import br.com.todeschini.persistence.packaging.usedpolyethylene.UsedPolyethyleneRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GhostGeneratorMethodsImpl implements GhostGeneratorMethods {

    private final GhostRepository repository;
    private final CornerBracketRepository cornerBracketRepository;
    private final NonwovenFabricRepository nonwovenFabricRepository;
    private final PlasticRepository plasticRepository;
    private final PolyethyleneRepository polyethyleneRepository;
    private final UsedCornerBracketRepository usedCornerBracketRepository;
    private final UsedNonwovenFabricRepository usedNonwovenFabricRepository;
    private final UsedPlasticRepository usedPlasticRepository;
    private final UsedPolyethyleneRepository usedPolyethyleneRepository;

    public GhostGeneratorMethodsImpl(GhostRepository repository, CornerBracketRepository cornerBracketRepository,
                                     NonwovenFabricRepository nonwovenFabricRepository, PlasticRepository plasticRepository,
                                     PolyethyleneRepository polyethyleneRepository, UsedCornerBracketRepository usedCornerBracketRepository,
                                     UsedNonwovenFabricRepository usedNonwovenFabricRepository, UsedPlasticRepository usedPlasticRepository,
                                     UsedPolyethyleneRepository usedPolyethyleneRepository) {
        this.repository = repository;
        this.cornerBracketRepository = cornerBracketRepository;
        this.nonwovenFabricRepository = nonwovenFabricRepository;
        this.plasticRepository = plasticRepository;
        this.polyethyleneRepository = polyethyleneRepository;
        this.usedCornerBracketRepository = usedCornerBracketRepository;
        this.usedNonwovenFabricRepository = usedNonwovenFabricRepository;
        this.usedPlasticRepository = usedPlasticRepository;
        this.usedPolyethyleneRepository = usedPolyethyleneRepository;
    }

    @Override
    @Transactional
    public void generateGhost(String ghostSuffix, Object father) {
        if (father instanceof Father fatherObject) {
            Ghost existingGhost = repository.findByCode(fatherObject.getCode().toString().substring(0,6) + ghostSuffix);
            if(existingGhost == null) {
                Ghost ghost = getGhost(ghostSuffix, fatherObject);
                fatherObject.setGhost(ghost);
                repository.save(ghost);
            } else{
                fatherObject.setGhost(existingGhost);
            }
        }
    }

    private static Ghost getGhost(String ghostSuffix, Father fatherObject) {
        Ghost ghost = new Ghost();
        ghost.setCode(fatherObject.getCode().toString().substring(0, 6) + ghostSuffix);
        ghost.setDescription(fatherObject.getDescription().substring(0, fatherObject.getDescription().lastIndexOf('-')).trim().toUpperCase());
        ghost.setMeasure1(fatherObject.getMeasure1());
        ghost.setMeasure2(fatherObject.getMeasure2());
        ghost.setMeasure3(fatherObject.getMeasure3());
        ghost.setSuffix(ghostSuffix);
        return ghost;
    }

    @Override
    @Transactional
    public void generateGhostStruct(DGhostGenerator ghostGenerator) {
        processCornerBracket((Ghost) ghostGenerator.getGhost(), ghostGenerator.getCornerBracketCode(), ghostGenerator.getQuantity());
        processNonwovenFabric((Ghost) ghostGenerator.getGhost(), ghostGenerator.getNonwovenFabricCode(), ghostGenerator.getOneFace());
        processPlastic((Ghost) ghostGenerator.getGhost(), ghostGenerator.getPlasticCode(), ghostGenerator.getUpper(), ghostGenerator.getAdditional(), ghostGenerator.getWidth());
        processPolyethylene((Ghost) ghostGenerator.getGhost(), ghostGenerator.getPolyethyleneCode());
        ((Ghost) ghostGenerator.getGhost()).calculateValue();
        repository.save((Ghost) ghostGenerator.getGhost());
    }

    private void processCornerBracket(Ghost ghost, Long cornerBracketCode, Integer quantity) {
        if (usedCornerBracketRepository.findByCornerBracketIdAndGhostId(cornerBracketCode, ghost.getCode()).isEmpty()) {
            CornerBracket cornerBracket = cornerBracketRepository.findById(cornerBracketCode)
                    .orElseThrow(() -> new ResourceNotFoundException("Cantoneira não encontrada"));
            UsedCornerBracket usedCornerBracket = new UsedCornerBracket();
            usedCornerBracket.setGhost(ghost);
            usedCornerBracket.setCornerBracket(cornerBracket);
            usedCornerBracket.setQuantity(quantity);
            usedCornerBracket.calculateNetQuantity();
            usedCornerBracket.calculateGrossQuantity();
            ghost.getCornerBrackets().add(usedCornerBracket);
            usedCornerBracketRepository.save(usedCornerBracket);
        }
    }

    private void processNonwovenFabric(Ghost ghost, Long nonwovenFabricCode, Boolean oneFace) {
        if (usedNonwovenFabricRepository.findByNonwovenFabricIdAndGhostId(nonwovenFabricCode, ghost.getCode()).isEmpty()) {
            NonwovenFabric nonwovenFabric = nonwovenFabricRepository.findById(nonwovenFabricCode)
                    .orElseThrow(() -> new ResourceNotFoundException("TNT não encontrado"));
            UsedNonwovenFabric usedNonwovenFabric = new UsedNonwovenFabric();
            usedNonwovenFabric.setGhost(ghost);
            usedNonwovenFabric.setNonwovenFabric(nonwovenFabric);
            usedNonwovenFabric.setOneFace(oneFace);
            usedNonwovenFabric.calculateNetQuantity();
            usedNonwovenFabric.calculateGrossQuantity();
            ghost.getNonwovenFabrics().add(usedNonwovenFabric);
            usedNonwovenFabricRepository.save(usedNonwovenFabric);
        }
    }

    private void processPlastic(Ghost ghost, Long plasticCode, Boolean upper, Double additional, Integer width) {
        if (usedPlasticRepository.findByPlasticIdAndGhostId(plasticCode, ghost.getCode()).isEmpty()) {
            Plastic plastic = plasticRepository.findById(plasticCode)
                    .orElseThrow(() -> new ResourceNotFoundException("Plástico não encontrado"));
            UsedPlastic usedPlastic = new UsedPlastic();
            usedPlastic.setGhost(ghost);
            usedPlastic.setPlastic(plastic);
            usedPlastic.setUpper(upper);
            usedPlastic.setAdditional(additional);
            usedPlastic.setWidth(width);
            usedPlastic.calculateNetQuantity();
            usedPlastic.calculateGrossQuantity();
            ghost.getPlastics().add(usedPlastic);
            usedPlasticRepository.save(usedPlastic);
        }
    }

    private void processPolyethylene(Ghost ghost, Long polyethyleneCode) {
        if (usedPolyethyleneRepository.findByPolyethyleneIdAndGhostId(polyethyleneCode, ghost.getCode()).isEmpty()) {
            Polyethylene polyethylene = polyethyleneRepository.findById(polyethyleneCode)
                    .orElseThrow(() -> new ResourceNotFoundException("Polietileno não encontrado"));
            UsedPolyethylene usedPolyethylene = new UsedPolyethylene();
            usedPolyethylene.setGhost(ghost);
            usedPolyethylene.setPolyethylene(polyethylene);
            usedPolyethylene.calculateNetQuantity();
            usedPolyethylene.calculateGrossQuantity();
            ghost.getPolyethylenes().add(usedPolyethylene);
            usedPolyethyleneRepository.save(usedPolyethylene);
        }
    }
}
