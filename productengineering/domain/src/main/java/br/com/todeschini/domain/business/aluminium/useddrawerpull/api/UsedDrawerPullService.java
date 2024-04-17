package br.com.todeschini.domain.business.aluminium.useddrawerpull.api;

import org.springframework.stereotype.Component;

@Component
public interface UsedDrawerPullService extends FindUsedDrawerPull, InsertUsedDrawerPull, UpdateUsedDrawerPull, DeleteUsedDrawerPull, InactivateUsedDrawerPull,
        FindAllActiveUsedDrawerPullAndCurrentOne {
}
