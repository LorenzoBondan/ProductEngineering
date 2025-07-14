package br.com.todeschini.notificationservicedomain.notification.spi;

import br.com.todeschini.libvalidationhandler.pageable.PageableRequest;
import br.com.todeschini.libvalidationhandler.pageable.Paged;
import br.com.todeschini.notificationservicedomain.notification.DNotification;

public interface CrudNotification {

    DNotification create(DNotification notification);
    DNotification update(Integer id, Boolean isRead);
    Paged<DNotification> findAllByUserId(Integer userId, PageableRequest pageableRequest);
}
