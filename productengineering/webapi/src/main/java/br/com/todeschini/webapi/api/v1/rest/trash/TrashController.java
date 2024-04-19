package br.com.todeschini.webapi.api.v1.rest.trash;

import br.com.todeschini.persistence.entities.trash.Trash;
import br.com.todeschini.persistence.trash.TrashRepository;
import br.com.todeschini.persistence.util.EntityService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/trash")
public class TrashController {

    private final TrashRepository repository;
    private final EntityService entityService;

    public TrashController(TrashRepository repository, EntityService entityService) {
        this.repository = repository;
        this.entityService = entityService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<?> findByUserAndDateRangeAndTable(
            @RequestParam(value = "username", required = false, defaultValue = "") String username,
            @RequestParam(value = "startDate", required = false, defaultValue = "0001-01-01T00:00:00Z") LocalDateTime startDate,
            @RequestParam(value = "endDate", required = false, defaultValue = "9999-12-31T23:59:59Z") LocalDateTime endDate,
            @RequestParam(value = "table", required = false, defaultValue = "") String table
    ){
        List<Trash> trashes = repository.findByUserAndDateRangeAndTable(username, startDate, endDate, table);
        return ResponseEntity.ok(trashes);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/retrieve/{id}")
    public ResponseEntity<?> retrieve(@PathVariable("id") Long id, @RequestParam("retrieveDependencies") Boolean retrieveDependencies){
        entityService.retrieve(id, retrieveDependencies);
        return ResponseEntity.ok().build();
    }
}
