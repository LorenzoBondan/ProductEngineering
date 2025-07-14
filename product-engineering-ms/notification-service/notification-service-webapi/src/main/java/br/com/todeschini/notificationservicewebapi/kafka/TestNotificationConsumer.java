package br.com.todeschini.notificationservicewebapi.kafka;

import br.com.todeschini.notificationservicedomain.events.TestNotificationEvent;
import br.com.todeschini.notificationservicedomain.notification.DNotification;
import br.com.todeschini.notificationservicedomain.notification.api.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TestNotificationConsumer {

    private final NotificationService notificationService;

    @KafkaListener(topics = "${topic.test-notification}", groupId = "notification-group", containerFactory = "testNotificationKafkaListenerContainerFactory")
    public void consume(TestNotificationEvent event) {
        String message = "Test notification sent to " + event.getUserName() + " with value U$ " + event.getAmount();
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

