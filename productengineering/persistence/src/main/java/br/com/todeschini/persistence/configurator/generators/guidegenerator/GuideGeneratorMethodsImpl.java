package br.com.todeschini.persistence.configurator.generators.guidegenerator;

import br.com.todeschini.domain.business.configurator.generators.guidegenerator.DGuideGenerator;
import br.com.todeschini.domain.business.configurator.generators.guidegenerator.spi.GuideGeneratorMethods;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.guides.Guide;
import br.com.todeschini.persistence.entities.guides.GuideMachine;
import br.com.todeschini.persistence.entities.guides.Machine;
import br.com.todeschini.persistence.entities.publico.Son;
import br.com.todeschini.persistence.guides.guide.GuideRepository;
import br.com.todeschini.persistence.guides.guidemachine.GuideMachineRepository;
import br.com.todeschini.persistence.guides.machine.MachineRepository;
import br.com.todeschini.persistence.publico.son.SonRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
public class GuideGeneratorMethodsImpl implements GuideGeneratorMethods {

    private final GuideRepository repository;
    private final GuideMachineRepository guideMachineRepository;
    private final SonRepository sonRepository;
    private final MachineRepository machineRepository;

    public GuideGeneratorMethodsImpl(GuideRepository repository, GuideMachineRepository guideMachineRepository, SonRepository sonRepository,
                                     MachineRepository machineRepository) {
        this.repository = repository;
        this.guideMachineRepository = guideMachineRepository;
        this.sonRepository = sonRepository;
        this.machineRepository = machineRepository;
    }

    @Override
    @Transactional
    public void generateGuideSon(DGuideGenerator guideGenerator) {
        if(((Son) guideGenerator.getSon()).getGuide() == null){
            Guide existingGuide = repository.findGuideBySixDigitCode(((Son) guideGenerator.getSon()).getCode().toString().substring(0,6));
            String description = ((Son) guideGenerator.getSon()).getDescription().substring(0, ((Son) guideGenerator.getSon()).getDescription().indexOf("-")).trim();
            Guide guide = existingGuide != null ? existingGuide : createNewGuide(description, guideGenerator.getImplementation(), guideGenerator.getFinalDate());
            associateSonWithGuide(((Son) guideGenerator.getSon()), guide);
            associateMachinesWithGuide(guide, guideGenerator.getMachinesIds(), ((Son) guideGenerator.getSon()).getMeasure1(), ((Son) guideGenerator.getSon()).getMeasure2(), ((Son) guideGenerator.getSon()).getMeasure3());
        }
    }

    private Guide createNewGuide(String description, LocalDate implementation, LocalDate finalDate) {
        Guide guide = new Guide();
        guide.setDescription(description);
        guide.setImplementation(implementation);
        guide.setFinalDate(finalDate);
        return repository.save(guide);
    }

    private void associateSonWithGuide(Son son, Guide guide) {
        son.setGuide(guide);
        guide.getSons().add(son);
        sonRepository.save(son);
        repository.save(guide);
    }

    private void associateMachinesWithGuide(Guide guide, List<Long> machinesIds, Integer measure1, Integer measure2, Integer measure3) {
        for (Long machineId : machinesIds) {
            Machine machine = machineRepository.findById(machineId)
                    .orElseThrow(() -> new ResourceNotFoundException("Machine Id not found"));
            if(guideMachineRepository.findByGuideIdAndMachineId(guide.getId(), machineId).isEmpty()){
                GuideMachine guideMachine = new GuideMachine();
                guideMachine.setGuide(guide);
                guideMachine.setMachine(machine);
                guideMachine.calculateTime(measure1, measure2, measure3);
                guideMachineRepository.save(guideMachine);
                guide.getGuideMachines().add(guideMachine);
            }
        }
        repository.save(guide);
    }
}
