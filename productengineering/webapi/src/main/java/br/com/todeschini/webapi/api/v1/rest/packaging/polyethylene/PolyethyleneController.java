package br.com.todeschini.webapi.api.v1.rest.packaging.polyethylene;

import br.com.todeschini.domain.business.packaging.polyethylene.DPolyethylene;
import br.com.todeschini.domain.business.packaging.polyethylene.api.PolyethyleneService;
import br.com.todeschini.persistence.entities.enums.Status;
import br.com.todeschini.persistence.packaging.polyethylene.PolyethyleneRepository;
import br.com.todeschini.webapi.api.v1.rest.packaging.polyethylene.projection.PolyethyleneDTO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Polyethylenes")
@RestController
@RequestMapping(value = "/polyethylenes")
public class PolyethyleneController {

    private final PolyethyleneService service;
    private final PolyethyleneRepository repository;

    public PolyethyleneController(PolyethyleneService service, PolyethyleneRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully search"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"), // when not logged
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ANALYST', 'ROLE_OPERATOR')")
    @GetMapping
    public ResponseEntity<?> findByStatusInAndDescriptionContainingIgnoreCase(@RequestParam(value = "status", required = false) String statusParam,
                                                                              @RequestParam(value = "description", required = false) String description,
                                                                              Pageable pageable){
        List<Status> statusList = (statusParam != null) ?
                Arrays.stream(statusParam.split(","))
                        .map(Status::valueOf)
                        .collect(Collectors.toList()) :
                List.of(Status.ACTIVE);
        return ResponseEntity.ok().body(repository.findByStatusInAndDescriptionContainingIgnoreCase(statusList, description, pageable, PolyethyleneDTO.class));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully search"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"), // when not logged
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ANALYST', 'ROLE_OPERATOR')")
    @GetMapping(value = "/activeAndCurrentOne")
    public ResponseEntity<List<DPolyethylene>> findAllActiveAndCurrentOne(@RequestParam(value = "id", required = false) Long id){
        List<DPolyethylene> list = service.findAllActiveAndCurrentOne(id);
        return ResponseEntity.ok().body(list);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request successfully executed"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"), // when not logged
            @ApiResponse(responseCode = "404", description = "Not found"), // when nonExisting id
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ANALYST', 'ROLE_OPERATOR')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<DPolyethylene> findById(@PathVariable Long id){
        DPolyethylene dto = service.find(id);
        return ResponseEntity.ok().body(dto);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"), // when not logged
            @ApiResponse(responseCode = "422", description = "Invalid data"), // when some attribute is not valid
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ANALYST')")
    @PostMapping
    public ResponseEntity<DPolyethylene> insert(@Valid @RequestBody DPolyethylene dto){
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{code}")
                .buildAndExpand(dto.getCode()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request successfully executed"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"), // when not logged
            @ApiResponse(responseCode = "404", description = "Not found"), // when nonExisting id
            @ApiResponse(responseCode = "422", description = "Invalid data"), // when some attribute is not valid
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ANALYST')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<DPolyethylene> update(@PathVariable Long id, @Valid @RequestBody DPolyethylene dto){
        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request successfully executed"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"), // when not logged
            @ApiResponse(responseCode = "404", description = "Not found"), // when nonExisting id
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ANALYST')")
    @PutMapping(value = "/inactivate/{id}")
    public ResponseEntity<DPolyethylene> inactivate(@PathVariable Long id){
        service.inactivate(id);
        return ResponseEntity.ok().build();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content, request successfully executed"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"), // when not logged
            @ApiResponse(responseCode = "404", description = "Not found"), // when nonExisting id
            @ApiResponse(responseCode = "409", description = "Integrity Violation"), // when object has relationships
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ANALYST')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<DPolyethylene> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
