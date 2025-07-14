package br.com.todeschini.notificationservicedomain.events;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatusNotificationEvent {

    private Integer userId;
    private Integer requestChangeId;
    private String status;
}
