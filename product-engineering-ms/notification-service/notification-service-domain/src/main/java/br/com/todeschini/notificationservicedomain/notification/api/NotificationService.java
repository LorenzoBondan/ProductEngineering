package br.com.todeschini.notificationservicedomain.notification.api;

import br.com.todeschini.libvalidationhandler.contracts.BaseService;
import br.com.todeschini.libvalidationhandler.pageable.PageableRequest;
import br.com.todeschini.libvalidationhandler.pageable.Paged;
import br.com.todeschini.notificationservicedomain.notification.DNotification;

public interface NotificationService extends BaseService<DNotification> {

    Paged<DNotification> findAllByUserId(Integer userId, PageableRequest pageableRequest);
    DNotification update(Integer id, Boolean isRead);
}
