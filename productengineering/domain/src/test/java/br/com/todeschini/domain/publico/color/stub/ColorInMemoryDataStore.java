package br.com.todeschini.domain.publico.color.stub;

import br.com.todeschini.domain.business.publico.color.DColor;
import br.com.todeschini.domain.business.publico.color.spi.CrudColor;
import br.com.todeschini.domain.metadata.Stub;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Stub
public class ColorInMemoryDataStore implements CrudColor {

    private final ConcurrentHashMap<Long, DColor> dataStore;

    public ColorInMemoryDataStore() {
        this.dataStore = new ConcurrentHashMap<>();
    }

    @Override
    public DColor insert(DColor obj) {
        obj.setCode(dataStore.keySet().stream().max(Comparator.naturalOrder()).orElse(0L) +1);
        dataStore.put(obj.getCode(), obj);
        return obj;
    }

    @Override
    public DColor update(Long id, DColor obj) {
        dataStore.put(obj.getCode(), obj);
        return obj;
    }


    @Override
    public DColor find(Long id) {
        return dataStore.get(id);
    }

    @Override
    public Collection<? extends DColor> findByName(String descricao) {
        return dataStore.values().stream().filter(Color -> Color.getName().equals(Color.getName())).toList();
    }

    @Override
    public void inactivate(Long id) {

    }

    @Override
    public List<DColor> findAllActiveAndCurrentOne(Long obj) {
        return null;
    }

    @Override
    public void delete(Long obj) {
        dataStore.remove(obj);
    }
}
