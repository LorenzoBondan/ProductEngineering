package br.com.todeschini.domain.business.guides.guide.api;

import org.springframework.stereotype.Component;

@Component
public interface GuideService extends FindGuide, InsertGuide, UpdateGuide, DeleteGuide, InactivateGuide,
        FindAllActiveGuideAndCurrentOne {
}
