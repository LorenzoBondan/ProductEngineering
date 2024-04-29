package br.com.todeschini.webapi.api.v1.rest.audit;

import br.com.todeschini.persistence.audit.THistoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/audit")
public class AuditController {

    private final THistoryRepository repository;

    public AuditController(THistoryRepository repository) {
        this.repository = repository;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<?> findByTabnameAndOperationAndIdNameAndIdValueAndDateRange(
            @RequestParam(value = "tabname", required = false, defaultValue = "") String tabname,
            @RequestParam(value = "operation", required = false, defaultValue = "") String operation,
            @RequestParam(value = "idName", required = false, defaultValue = "") String idName,
            @RequestParam(value = "idValue", required = false, defaultValue = "") String idValue,
            @RequestParam(value = "startDate", required = false, defaultValue = "0001-01-01T00:00:00Z") LocalDateTime startDate,
            @RequestParam(value = "endDate", required = false, defaultValue = "9999-12-31T23:59:59Z") LocalDateTime endDate
    ){
        return ResponseEntity.ok(repository.findByTabnameAndOperationAndIdNameAndIdValueAndDateRange(tabname, operation, idName, idValue, startDate, endDate));
    }
}
