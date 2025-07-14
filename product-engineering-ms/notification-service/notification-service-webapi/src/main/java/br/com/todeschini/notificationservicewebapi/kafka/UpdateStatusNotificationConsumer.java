package br.com.todeschini.notificationservicewebapi.kafka;

import br.com.todeschini.notificationservicedomain.events.UpdateStatusNotificationEvent;
import br.com.todeschini.notificationservicedomain.notification.DNotification;
import br.com.todeschini.notificationservicedomain.notification.api.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class UpdateStatusNotificationConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "${topic.update-status-notification}", groupId = "notification-group", containerFactory = "updateStatusNotificationKafkaListenerContainerFactory")
    public void consume(UpdateStatusNotificationEvent event) {
        String message = "Your Request Change with id: " + event.getRequestChangeId() + " had the status changed to " + event.getStatus();
        notificationService.create(DNotification.builder()
                        .userId(event.getUserId())
                        .message(message)
                        .date(LocalDateTime.now())
                        .isRead(false)
                .build());
        System.out.println("🔔 Received Notification:");
        System.out.println(message);
    }
}

