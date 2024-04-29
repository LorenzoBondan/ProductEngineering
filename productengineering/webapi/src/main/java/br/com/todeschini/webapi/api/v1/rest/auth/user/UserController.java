package br.com.todeschini.webapi.api.v1.rest.auth.user;

import br.com.todeschini.domain.business.auth.authservice.api.AuthService;
import br.com.todeschini.domain.business.auth.user.DUser;
import br.com.todeschini.domain.business.auth.user.api.UserService;
import br.com.todeschini.persistence.auth.user.UserRepository;
import br.com.todeschini.persistence.entities.enums.Status;
import br.com.todeschini.webapi.api.v1.rest.auth.user.projection.UserDTO;
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
import java.util.List;

@Tag(name = "Users")
@RestController
@RequestMapping(value = "/users")
public class UserController {
    
    private final UserService service;
    private final AuthService authService;
    private final UserRepository repository;

    public UserController(UserService service, AuthService authService, UserRepository repository) {
        this.service = service;
        this.authService = authService;
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
    public ResponseEntity<?> findByStatusInAndNameContainingIgnoreCase(@RequestBody(required = false) List<Status> statusList,
                                                                       @RequestParam(value = "name", required = false, defaultValue = "") String name,
                                                                       Pageable pageable){
        statusList = (statusList == null) ? List.of(Status.ACTIVE) : statusList;
        return ResponseEntity.ok().body(repository.findByStatusInAndNameContainingIgnoreCase(statusList, name, pageable, UserDTO.class));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully search"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"), // when not logged
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/activeAndCurrentOne")
    public ResponseEntity<List<DUser>> findAllActiveAndCurrentOne(@RequestParam(value = "id", required = false) Long id){
        List<DUser> list = service.findAllActiveAndCurrentOne(id);
        return ResponseEntity.ok().body(list);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request successfully executed"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"), // when not logged
            @ApiResponse(responseCode = "403", description = "Forbidden"), // when nonAdmin try to execute the request
            @ApiResponse(responseCode = "404", description = "Not found"), // when nonExisting id
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ANALYST', 'ROLE_OPERATOR')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<DUser> findById(@PathVariable Long id) {
        authService.validateSelfOrAdmin(id);
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
    public ResponseEntity<DUser> insert(@Valid @RequestBody DUser domain){
        DUser newDomain = service.insert(domain);
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
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ANALYST', 'ROLE_OPERATOR')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<DUser> update(@PathVariable Long id, @Valid @RequestBody DUser domain){
        authService.validateSelfOrAdmin(id);
        return ResponseEntity.ok().body(service.update(id, domain));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request successfully executed"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"), // when not logged
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ANALYST', 'ROLE_OPERATOR')")
    @PutMapping(value = "/password")
    public ResponseEntity<DUser> updatePassword(@RequestParam("newPassword") String newPassword,
                                                @RequestParam("oldPassword") String oldPassword){
        service.updatePassword(newPassword, oldPassword);
        return ResponseEntity.ok().build();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Request successfully executed"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"), // when not logged
            @ApiResponse(responseCode = "404", description = "Not found"), // when nonExisting id
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/inactivate/{id}")
    public ResponseEntity<DUser> inactivate(@PathVariable Long id){
        service.inactivate(id);
        return ResponseEntity.ok().build();
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
    public ResponseEntity<DUser> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
