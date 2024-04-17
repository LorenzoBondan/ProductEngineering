package br.com.todeschini.persistence.mdp.usededgebanding;

import br.com.todeschini.domain.business.mdp.usededgebanding.DUsedEdgeBanding;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.mdp.MDPSon;
import br.com.todeschini.persistence.entities.mdp.UsedEdgeBanding;
import br.com.todeschini.persistence.mdp.edgebanding.EdgeBandingRepository;
import br.com.todeschini.persistence.publico.item.ItemRepository;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class UsedEdgeBandingDomainToEntityAdapter implements Convertable<UsedEdgeBanding, DUsedEdgeBanding> {

    private final EdgeBandingRepository edgeBandingRepository;
    private final ItemRepository itemRepository;

    public UsedEdgeBandingDomainToEntityAdapter(EdgeBandingRepository edgeBandingRepository, ItemRepository itemRepository){
        this.edgeBandingRepository = edgeBandingRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public UsedEdgeBanding toEntity(DUsedEdgeBanding domain) {
        UsedEdgeBanding entity = new UsedEdgeBanding();
        entity.setId(domain.getId());
        entity.setEdgeBanding(edgeBandingRepository.findById(domain.getEdgeBandingCode()).orElseThrow(() -> new ResourceNotFoundException("Fita Borda não encontrada")));
        if(itemRepository.existsById(domain.getMdpSonCode())){
            entity.setSon((MDPSon) itemRepository.findById(domain.getMdpSonCode()).get());
        } else {
            throw new ResourceNotFoundException("Código do filho não encontrado: " + domain.getMdpSonCode());
        }
        entity.setNetQuantity(domain.getNetQuantity());
        entity.setGrossQuantity(domain.getGrossQuantity());
        return entity;
    }

    @Override
    public DUsedEdgeBanding toDomain(UsedEdgeBanding entity) {
        DUsedEdgeBanding domain = new DUsedEdgeBanding();
        domain.setId(entity.getId());
        domain.setEdgeBandingCode(entity.getEdgeBanding().getCode());
        domain.setMdpSonCode(entity.getSon().getCode());
        domain.setNetQuantity(entity.getNetQuantity());
        domain.setGrossQuantity(entity.getGrossQuantity());
        domain.setMeasurementUnit(entity.getMeasurementUnit());
        return domain;
    }
}
