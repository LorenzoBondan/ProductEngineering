package br.com.todeschini.webapi.api.v1.rest.lixeira;

import br.com.todeschini.persistence.entities.publico.Lixeira;
import br.com.todeschini.persistence.publico.lixeira.LixeiraRepository;
import br.com.todeschini.persistence.util.EntityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/lixeira")
public class LixeiraController {

    private final LixeiraRepository repository;
    private final EntityService entityService;

    public LixeiraController(LixeiraRepository repository, EntityService entityService) {
        this.repository = repository;
        this.entityService = entityService;
    }

    @Operation(summary = "Pesquisar todos os itens na Lixeira por usuário que deletou, intervalo de datas da exclusão e nome da tabela", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ANALYST')")
    @GetMapping
    public ResponseEntity<?> pesquisarTodosPorUsuarioEPeriodoDeDatasETabela(
            @RequestParam(value = "usuario", required = false, defaultValue = "") String usuario,
            @RequestParam(value = "dataInicial", required = false, defaultValue = "0001-01-01T00:00:00Z") LocalDateTime dataInicial,
            @RequestParam(value = "dataFinal", required = false, defaultValue = "9999-12-31T23:59:59Z") LocalDateTime dataFinal,
            @RequestParam(value = "tabela", required = false, defaultValue = "") String tabela
    ){
        List<Lixeira> lixeiras = repository.findByUsuarioAndDataRangeAndTabela(usuario, dataInicial, dataFinal, tabela);
        return ResponseEntity.ok(lixeiras);
    }

    @Operation(summary = "Recupera um item da lixeira por id, juntamente com suas dependências ou não", method = "GET", description = "Muda o status do objeto de 'Lixeira' para 'Ativo'")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ANALYST')")
    @GetMapping(value = "/recuperar/{id}")
    public ResponseEntity<?> recuperar(@PathVariable("id") Integer id, @RequestParam("recuperarDependencias") Boolean recuperarDependencias){
        entityService.recuperarDaLixeira(id, recuperarDependencias);
        return ResponseEntity.ok().build();
    }
}
