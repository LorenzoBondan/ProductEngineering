package br.com.todeschini.notificationservicepersistence.notification;

import br.com.todeschini.libvalidationhandler.contracts.Convertable;
import br.com.todeschini.notificationservicedomain.notification.DNotification;
import br.com.todeschini.notificationservicepersistence.entities.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationDomainToEntityAdapter implements Convertable<Notification, DNotification> {

    @Override
    public Notification toEntity(DNotification domain) {
        return Notification.builder()
                .id(domain.getId())
                .userId(domain.getUserId())
                .message(domain.getMessage())
                .isRead(domain.getIsRead())
                .date(domain.getDate())
                .build();
    }

    @Override
    public DNotification toDomain(Notification entity) {
        return DNotification.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .message(entity.getMessage())
                .isRead(entity.getIsRead())
                .date(entity.getDate())
                .build();
    }
}
