package br.com.todeschini.domain.business.guides.guide;

import br.com.todeschini.domain.business.guides.guide.api.GuideService;
import br.com.todeschini.domain.business.guides.guide.spi.CrudGuide;
import br.com.todeschini.domain.metadata.DomainService;

import java.util.List;

@DomainService
public class GuideServiceImpl implements GuideService {

    private final CrudGuide crudGuide;

    public GuideServiceImpl(CrudGuide crudGuide) {
        this.crudGuide = crudGuide;
    }

    @Override
    public List<DGuide> findAllActiveAndCurrentOne(Long id) {
        return crudGuide.findAllActiveAndCurrentOne(id);
    }

    @Override
    public DGuide find(Long id) {
        return crudGuide.find(id);
    }

    @Override
    public DGuide insert(DGuide domain) {
        domain.validate();
        return crudGuide.insert(domain);
    }

    @Override
    public DGuide update(Long id, DGuide domain) {
        domain.validate();
        return crudGuide.update(id, domain);
    }

    @Override
    public void inactivate(Long id) {
        crudGuide.inactivate(id);
    }

    @Override
    public void delete(Long id) {
        crudGuide.delete(id);
    }
}
