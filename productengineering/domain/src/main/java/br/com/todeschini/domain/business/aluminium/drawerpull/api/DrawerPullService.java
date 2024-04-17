package br.com.todeschini.domain.business.aluminium.drawerpull.api;

import org.springframework.stereotype.Component;

@Component
public interface DrawerPullService extends FindDrawerPull, InsertDrawerPull, UpdateDrawerPull, DeleteDrawerPull, InactivateDrawerPull,
        FindAllActiveDrawerPullAndCurrentOne {
}
