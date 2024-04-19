package br.com.todeschini.webapi.api.v1.rest.auth.role;

import br.com.todeschini.domain.business.auth.role.DRole;
import br.com.todeschini.domain.business.auth.role.api.RoleService;
import br.com.todeschini.persistence.auth.role.RoleRepository;
import br.com.todeschini.webapi.api.v1.rest.auth.role.projection.RoleDTO;
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

@Tag(name = "Roles")
@RestController
@RequestMapping(value = "/roles")
public class RoleController {
    
    private final RoleService service;
    private final RoleRepository repository;

    public RoleController(RoleService service, RoleRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully search"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"), // when not logged
            @ApiResponse(responseCode = "403", description = "Forbidden"), // when nonAdmin try to execute the request
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<?> findByAuthorityStartingWithIgnoreCase(@RequestParam(value = "authority", required = false, defaultValue = "") String authority, Pageable pageable){
        return ResponseEntity.ok().body(repository.findByAuthorityStartingWithIgnoreCase(authority, pageable, RoleDTO.class));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request successfully executed"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"), // when not logged
            @ApiResponse(responseCode = "403", description = "Forbidden"), // when nonAdmin try to execute the request
            @ApiResponse(responseCode = "404", description = "Not found"), // when nonExisting id
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<DRole> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.find(id));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request"), // invalid data, String in Integer field
            @ApiResponse(responseCode = "401", description = "Unauthorized"), // when not logged
            @ApiResponse(responseCode = "403", description = "Forbidden"), // when nonAdmin try to execute the request
            @ApiResponse(responseCode = "422", description = "Invalid data"), // when some attribute is not valid
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<DRole> insert(@Valid @RequestBody DRole domain){
        DRole newDomain = service.insert(domain);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(domain.getId()).toUri();
        return ResponseEntity.created(uri).body(newDomain);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request successfully executed"),
            @ApiResponse(responseCode = "400", description = "Bad Request"), // invalid data, String in Integer field
            @ApiResponse(responseCode = "401", description = "Unauthorized"), // when not logged
            @ApiResponse(responseCode = "403", description = "Forbidden"), // when nonAdmin try to execute the request
            @ApiResponse(responseCode = "404", description = "Not found"), // when nonExisting id
            @ApiResponse(responseCode = "422", description = "Invalid data"), // when some attribute is not valid
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<DRole> update(@PathVariable Long id, @Valid @RequestBody DRole domain){
        return ResponseEntity.ok().body(service.update(id, domain));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content, request successfully executed"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"), // when not logged
            @ApiResponse(responseCode = "403", description = "Forbidden"), // when nonAdmin try to execute the request
            @ApiResponse(responseCode = "404", description = "Not found"), // when nonExisting id
            @ApiResponse(responseCode = "409", description = "Integrity Violation"), // when object has relationships
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<DRole> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
