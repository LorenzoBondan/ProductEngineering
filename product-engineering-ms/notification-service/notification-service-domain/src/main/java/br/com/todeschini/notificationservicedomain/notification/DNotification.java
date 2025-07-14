package br.com.todeschini.notificationservicedomain.notification;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DNotification {

    private Integer id;
    private Integer userId;
    private String message;
    private LocalDateTime date;
    private Boolean isRead = Boolean.FALSE;
}
