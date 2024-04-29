package br.com.todeschini.webapi.api.v1.rest.guides.guide;

import br.com.todeschini.domain.business.guides.guide.DGuide;
import br.com.todeschini.domain.business.guides.guide.api.GuideService;
import br.com.todeschini.persistence.entities.enums.Status;
import br.com.todeschini.persistence.guides.guide.GuideRepository;
import br.com.todeschini.webapi.api.v1.rest.guides.guide.projection.GuideDTO;
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

@Tag(name = "Guides")
@RestController
@RequestMapping(value = "/guides")
public class GuideController {

    private final GuideService service;
    private final GuideRepository repository;

    public GuideController(GuideService service, GuideRepository repository) {
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
    public ResponseEntity<?> findByStatusIn(@RequestParam(value = "status", required = false) String statusParam,
                                            Pageable pageable){
        List<Status> statusList = (statusParam != null) ?
                Arrays.stream(statusParam.split(","))
                        .map(Status::valueOf)
                        .collect(Collectors.toList()) :
                List.of(Status.ACTIVE);
        return ResponseEntity.ok().body(repository.findByStatusIn(statusList, pageable, GuideDTO.class));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully search"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"), // when not logged
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ANALYST', 'ROLE_OPERATOR')")
    @GetMapping(value = "/activeAndCurrentOne")
    public ResponseEntity<List<DGuide>> findAllActiveAndCurrentOne(@RequestParam(value = "id", required = false) Long id){
        List<DGuide> list = service.findAllActiveAndCurrentOne(id);
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
    public ResponseEntity<DGuide> findById(@PathVariable Long id){
        DGuide dto = service.find(id);
        return ResponseEntity.ok().body(dto);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"), // when not logged
            @ApiResponse(responseCode = "422", description = "Invalid data"), // when some attribute is not valid
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ANALYST', 'ROLE_OPERATOR')")
    @PostMapping
    public ResponseEntity<DGuide> insert(@Valid @RequestBody DGuide dto){
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request successfully executed"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"), // when not logged
            @ApiResponse(responseCode = "404", description = "Not found"), // when nonExisting id
            @ApiResponse(responseCode = "422", description = "Invalid data"), // when some attribute is not valid
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ANALYST', 'ROLE_OPERATOR')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<DGuide> update(@PathVariable Long id, @Valid @RequestBody DGuide dto){
        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request successfully executed"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"), // when not logged
            @ApiResponse(responseCode = "404", description = "Not found"), // when nonExisting id
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ANALYST', 'ROLE_OPERATOR')")
    @PutMapping(value = "/inactivate/{id}")
    public ResponseEntity<DGuide> inactivate(@PathVariable Long id){
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
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ANALYST', 'ROLE_OPERATOR')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<DGuide> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
