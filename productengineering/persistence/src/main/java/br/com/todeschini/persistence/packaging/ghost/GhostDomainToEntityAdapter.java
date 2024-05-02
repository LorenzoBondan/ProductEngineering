package br.com.todeschini.persistence.packaging.ghost;

import br.com.todeschini.domain.business.packaging.ghost.DGhost;
import br.com.todeschini.persistence.entities.packaging.Ghost;
import br.com.todeschini.persistence.packaging.usedcornerbracket.UsedCornerBracketDomainToEntityAdapter;
import br.com.todeschini.persistence.packaging.usedcornerbracket.UsedCornerBracketRepository;
import br.com.todeschini.persistence.packaging.usednonwovenfabric.UsedNonwovenFabricDomainToEntityAdapter;
import br.com.todeschini.persistence.packaging.usednonwovenfabric.UsedNonwovenFabricRepository;
import br.com.todeschini.persistence.packaging.usedplastic.UsedPlasticDomainToEntityAdapter;
import br.com.todeschini.persistence.packaging.usedplastic.UsedPlasticRepository;
import br.com.todeschini.persistence.packaging.usedpolyethylene.UsedPolyethyleneDomainToEntityAdapter;
import br.com.todeschini.persistence.packaging.usedpolyethylene.UsedPolyethyleneRepository;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class GhostDomainToEntityAdapter implements Convertable<Ghost, DGhost> {

    private final UsedCornerBracketRepository usedCornerBracketRepository;
    private final UsedCornerBracketDomainToEntityAdapter usedCornerBracketAdapter;
    private final UsedNonwovenFabricRepository usedNonwovenFabricRepository;
    private final UsedNonwovenFabricDomainToEntityAdapter usedNonwovenFabricAdapter;
    private final UsedPlasticRepository usedPlasticRepository;
    private final UsedPlasticDomainToEntityAdapter usedPlasticAdapter;
    private final UsedPolyethyleneRepository usedPolyethyleneRepository;
    private final UsedPolyethyleneDomainToEntityAdapter usedPolyethyleneAdapter;

    public GhostDomainToEntityAdapter(UsedCornerBracketRepository usedCornerBracketRepository, UsedCornerBracketDomainToEntityAdapter usedCornerBracketAdapter,
                                      UsedNonwovenFabricRepository usedNonwovenFabricRepository, UsedNonwovenFabricDomainToEntityAdapter usedNonwovenFabricAdapter,
                                      UsedPlasticRepository usedPlasticRepository, UsedPlasticDomainToEntityAdapter usedPlasticAdapter,
                                      UsedPolyethyleneRepository usedPolyethyleneRepository, UsedPolyethyleneDomainToEntityAdapter usedPolyethyleneAdapter) {
        this.usedCornerBracketRepository = usedCornerBracketRepository;
        this.usedCornerBracketAdapter = usedCornerBracketAdapter;
        this.usedNonwovenFabricRepository = usedNonwovenFabricRepository;
        this.usedNonwovenFabricAdapter = usedNonwovenFabricAdapter;
        this.usedPlasticRepository = usedPlasticRepository;
        this.usedPlasticAdapter = usedPlasticAdapter;
        this.usedPolyethyleneRepository = usedPolyethyleneRepository;
        this.usedPolyethyleneAdapter = usedPolyethyleneAdapter;
    }

    @Override
    public Ghost toEntity(DGhost domain) {
        return Ghost.builder()
                .code(domain.getCode())
                .suffix(domain.getSuffix())
                .description(domain.getDescription())
                .measure1(domain.getMeasure1())
                .measure2(domain.getMeasure2())
                .measure3(domain.getMeasure3())

                .cornerBrackets(domain.getCornerBrackets().stream().map(cornerBracket -> usedCornerBracketRepository.findById(cornerBracket.getId()).get()).collect(Collectors.toSet()))
                .nonwovenFabrics(domain.getNonwovenFabrics().stream().map(nonwovenFabric -> usedNonwovenFabricRepository.findById(nonwovenFabric.getId()).get()).collect(Collectors.toSet()))
                .plastics(domain.getPlastics().stream().map(plastic -> usedPlasticRepository.findById(plastic.getId()).get()).collect(Collectors.toSet()))
                .polyethylenes(domain.getPolyethylenes().stream().map(polyethylene -> usedPolyethyleneRepository.findById(polyethylene.getId()).get()).collect(Collectors.toSet()))

                .build();
    }

    @Override
    public DGhost toDomain(Ghost entity) {
        return DGhost.builder()
                .code(entity.getCode())
                .suffix(entity.getSuffix())
                .description(entity.getDescription())
                .measure1(entity.getMeasure1())
                .measure2(entity.getMeasure2())
                .measure3(entity.getMeasure3())

                .cornerBrackets(entity.getCornerBrackets().stream().map(usedCornerBracketAdapter::toDomain).collect(Collectors.toList()))
                .nonwovenFabrics(entity.getNonwovenFabrics().stream().map(usedNonwovenFabricAdapter::toDomain).collect(Collectors.toList()))
                .plastics(entity.getPlastics().stream().map(usedPlasticAdapter::toDomain).collect(Collectors.toList()))
                .polyethylenes(entity.getPolyethylenes().stream().map(usedPolyethyleneAdapter::toDomain).collect(Collectors.toList()))

                .value(Math.round(entity.calculateValue() * 1e2) / 1e2)
                .build();
    }
}
