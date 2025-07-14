package br.com.todeschini.notificationservicepersistence.notification;

import br.com.todeschini.libexceptionhandler.exceptions.ResourceNotFoundException;
import br.com.todeschini.libspecificationhandler.PageRequestUtils;
import br.com.todeschini.libvalidationhandler.pageable.PageableRequest;
import br.com.todeschini.libvalidationhandler.pageable.Paged;
import br.com.todeschini.libvalidationhandler.pageable.PagedBuilder;
import br.com.todeschini.notificationservicedomain.notification.DNotification;
import br.com.todeschini.notificationservicedomain.notification.spi.CrudNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CrudNotificationImpl implements CrudNotification {

    private final NotificationRepository repository;
    private final NotificationDomainToEntityAdapter adapter;
    private final PageRequestUtils pageRequestUtils;

    @Override
    public Paged<DNotification> findAllByUserId(Integer userId, PageableRequest pageableRequest) {
        return Optional.of(repository.findAllByUserId(userId, pageRequestUtils.toPage(pageableRequest)))
                .map(r -> new PagedBuilder<DNotification>()
                        .withContent(r.getContent().stream().map(adapter::toDomain).toList())
                        .withSortedBy(String.join(";", pageableRequest.getSort()))
                        .withFirst(r.isFirst())
                        .withLast(r.isLast())
                        .withPage(r.getNumber())
                        .withSize(r.getSize())
                        .withTotalPages(r.getTotalPages())
                        .withNumberOfElements(Math.toIntExact(r.getTotalElements()))
                        .build())
                .orElse(null);
    }

    @Override
    public DNotification create(DNotification notification) {
        return adapter.toDomain(repository.save(adapter.toEntity(notification)));
    }

    @Override
    public DNotification update(Integer id, Boolean isRead) {
        DNotification notification = repository.findById(id)
                .map(adapter::toDomain)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
        notification.setIsRead(isRead);
        return adapter.toDomain(repository.save(adapter.toEntity(notification)));
    }
}
