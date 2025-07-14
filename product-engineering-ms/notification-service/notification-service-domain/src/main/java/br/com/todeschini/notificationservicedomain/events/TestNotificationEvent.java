package br.com.todeschini.notificationservicedomain.events;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TestNotificationEvent {

    private Integer userId;
    private String userName;
    private Double amount;
}
