package br.com.todeschini.persistence.aluminium.usedtrysquare;

import br.com.todeschini.domain.business.aluminium.usedtrysquare.DUsedTrySquare;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.aluminium.trysquare.TrySquareRepository;
import br.com.todeschini.persistence.entities.aluminium.AluminiumSon;
import br.com.todeschini.persistence.entities.aluminium.UsedTrySquare;
import br.com.todeschini.persistence.publico.item.ItemRepository;
import br.com.todeschini.persistence.util.Convertable;
import org.springframework.stereotype.Component;

@Component
public class UsedTrySquareDomainToEntityAdapter implements Convertable<UsedTrySquare, DUsedTrySquare> {

    private final TrySquareRepository trySquareRepository;
    private final ItemRepository itemRepository;

    public UsedTrySquareDomainToEntityAdapter(TrySquareRepository trySquareRepository, ItemRepository itemRepository){
        this.trySquareRepository = trySquareRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public UsedTrySquare toEntity(DUsedTrySquare domain) {
        UsedTrySquare entity = new UsedTrySquare();
        entity.setId(domain.getId());
        entity.setTrySquare(trySquareRepository.findById(domain.getTrySquareCode()).orElseThrow(() -> new ResourceNotFoundException("Esquadreta não encontrada")));
        if(itemRepository.existsById(domain.getAluminiumSonCode())){
            entity.setAluminiumSon((AluminiumSon) itemRepository.findById(domain.getAluminiumSonCode()).get());
        } else {
            throw new ResourceNotFoundException("Código do filho não encontrado: " + domain.getAluminiumSonCode());
        }
        entity.setQuantity(domain.getQuantity());
        return entity;
    }

    @Override
    public DUsedTrySquare toDomain(UsedTrySquare entity) {
        DUsedTrySquare domain = new DUsedTrySquare();
        domain.setId(entity.getId());
        domain.setTrySquareCode(entity.getTrySquare().getCode());
        domain.setAluminiumSonCode(entity.getAluminiumSon().getCode());
        domain.setQuantity(entity.getQuantity());
        domain.setMeasurementUnit(entity.getMeasurementUnit());
        return domain;
    }
}
