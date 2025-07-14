package br.com.todeschini.notificationservicedomain.notification;

import br.com.todeschini.libauthservicedomain.auth.api.AuthService;
import br.com.todeschini.libvalidationhandler.metadata.DomainService;
import br.com.todeschini.libvalidationhandler.pageable.PageableRequest;
import br.com.todeschini.libvalidationhandler.pageable.Paged;
import br.com.todeschini.notificationservicedomain.notification.api.NotificationService;
import br.com.todeschini.notificationservicedomain.notification.spi.CrudNotification;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final CrudNotification crudNotification;
    private final AuthService authService;

    @Override
    public Paged<DNotification> findAllByUserId(Integer userId, PageableRequest pageableRequest) {
        authService.validateSelfOrAdmin(userId);
        return crudNotification.findAllByUserId(userId, pageableRequest);
    }

    @Override
    public DNotification create(DNotification notification) {
        return crudNotification.create(notification);
    }

    @Override
    public DNotification update(Integer id, Boolean isRead) {
        return crudNotification.update(id, isRead);
    }

    @Override
    public Paged<DNotification> findAll(PageableRequest request) {
        return null;
    }

    @Override
    public DNotification find(Integer id) {
        return null;
    }

    @Override
    public DNotification update(DNotification obj) {
        return null;
    }

    @Override
    public void delete(Integer id) {
    }
}
