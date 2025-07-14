package br.com.todeschini.notificationservicewebapi.controller;

import br.com.todeschini.libvalidationhandler.pageable.PageableRequest;
import br.com.todeschini.libvalidationhandler.pageable.Paged;
import br.com.todeschini.notificationservicedomain.notification.DNotification;
import br.com.todeschini.notificationservicedomain.notification.api.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService service;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ANALYST', 'ROLE_OPERATOR')")
    @GetMapping(value = "/user/{id}")
    public ResponseEntity<Paged<DNotification>> findAllByUserId(@PathVariable("id") Integer userId,
                                                                @RequestParam(value = "columns") List<String> columns,
                                                                @RequestParam(value = "operations", required = false, defaultValue = "=") List<String> operations,
                                                                @RequestParam(value = "values", required = false) List<String> values,
                                                                @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                                @RequestParam(value = "sort") List<String> sort) {
        if (values == null || values.isEmpty()) {
            values = List.of("");
        }
        PageableRequest pageable = PageableRequest.builder()
                .page(page)
                .pageSize(pageSize)
                .sort(sort.toArray(new String[0]))
                .columns(columns)
                .operations(operations)
                .values(values)
                .columnMap(Map.of(
                        "id", "id",
                        "userId", "userId",
                        "message", "message",
                        "date", "date",
                        "isRead", "isRead"))
                .build();

        return ResponseEntity.ok(service.findAllByUserId(userId, pageable));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ANALYST', 'ROLE_OPERATOR')")
    @PatchMapping(value = "/{id}")
    public ResponseEntity<DNotification> update(@PathVariable("id") Integer id,
                                                @RequestParam(value = "isRead", required = false, defaultValue = "true") Boolean isRead) {
        return ResponseEntity.ok(service.update(id, isRead));
    }
}
