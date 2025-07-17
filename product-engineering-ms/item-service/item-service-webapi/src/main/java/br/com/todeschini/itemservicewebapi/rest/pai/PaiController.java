package br.com.todeschini.itemservicewebapi.rest.pai;

import br.com.todeschini.itemservicedomain.pai.DPai;
import br.com.todeschini.itemservicedomain.pai.api.PaiService;
import br.com.todeschini.itemservicedomain.pai.montadores.DMontadorEstruturaPai;
import br.com.todeschini.itemservicedomain.pai.montadores.DMontadorEstruturaPaiModulacao;
import br.com.todeschini.libvalidationhandler.pageable.PageableRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "Pai")
@RestController
@RequestMapping("/api/pai")
@RequiredArgsConstructor
public class PaiController {

    private final PaiService service;

    /**
     * @param colunas representam as colunas da tabela a serem pesquisadas
     * @param operacoes representam se as comparações serão de igualdade, maior que, menor que ou diferente de
     * @param valores representam os valores a serem usados como parâmetros de busca
     * @param page atributo da paginação, representa o número da página da busca
     * @param pageSize atributo da paginação, representa o total de elementos em uma página
     * @param sort atributo da paginação, representa a ordenação
     */
    @Operation(summary = "Pesquisar todos os Pais por colunas, operações e valores", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "application/json", schema = @Schema(
                    example = """
                    {
                        "numberOfElements": 150,
                        "page": 0,
                        "totalPages": 15,
                        "size": 10,
                        "first": true,
                        "last": false,
                        "sortedBy": "codigo;d",
                         "content": []
                    }
                    """))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ANALYST', 'ROLE_OPERATOR')")
    @GetMapping
    public ResponseEntity<?> pesquisar(
            @RequestParam(value = "colunas") List<String> colunas,
            @RequestParam(value = "operacoes", required = false, defaultValue = "=") List<String> operacoes,
            @RequestParam(value = "valores", required = false) List<String> valores,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sort") List<String> sort) {

        if (valores == null || valores.isEmpty()) {
            valores = List.of("");
        }

        return ResponseEntity.ok(
                service.buscar(PageableRequest.builder()
                        .page(page)
                        .pageSize(pageSize)
                        .sort(sort.toArray(String[]::new))
                        .columns(colunas)
                        .operations(operacoes)
                        .values(valores)
                        .columnMap(Map.of(
                                "codigo", "cdpai",
                                "descricao", "descricao",
                                "bordasComprimento", "bordasComprimento",
                                "bordasLargura", "bordasLargura",
                                "numeroCantoneiras", "numeroCantoneiras",
                                "tntUmaFace", "tntUmaFace",
                                "plasticoAcima", "plasticoAcima",
                                "plasticoAdicional", "plasticoAdicional",
                                "larguraPlastico", "larguraPlastico",
                                "situacao", "situacao"))
                        .build())
        );
    }

    /**
     * @param id representa o ID do Pai a ser pesquisado
     */
    @Operation(summary = "Pesquisar um Pai por id", method = "GET", description = "Pesquisa um objeto por id, independente da sua situação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ANALYST', 'ROLE_OPERATOR')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> pesquisarPorId(@PathVariable("id") Integer id){
        return ResponseEntity.ok(service.buscar(id));
    }

    /**
     * @param dto representa o objeto Pai a ser criado
     */
    @Operation(summary = "Inserir um novo Pai", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "409", description = "Conflict"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity")
    })
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ANALYST', 'ROLE_OPERATOR')")
    @PostMapping
    public ResponseEntity<?> criar(
            @RequestBody
            @Schema(
                    description = "Objeto de Pai para criação",
                    requiredProperties = "descricao",
                    example = """
                    {
                        "modelo": {
                            "codigo": 1
                        },
                        "categoriaComponente": {
                            "codigo": 1
                        },
                        "descricao": "Descrição",
                        "bordasComprimento": 2,
                        "bordasLargura": 2,
                        "numeroCantoneiras": 4,
                        "tntUmaFace": true,
                        "plasticoAcima": true,
                        "plasticoAdicional": 100.0,
                        "larguraPlastico": 100
                    }
                    """
            )
            DPai dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.incluir(dto));
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ANALYST, ROLE_OPERATOR')")
    @PostMapping(value = "/estrutura")
    public ResponseEntity<?> criarEstrutura(
            @RequestBody
            @Schema(
                    description = "Cadastro e criação de estrutura de um Pai",
                    requiredProperties = "descricao",
                    example = """
                    {
                        "descricao": "Descrição"
                    }
                    """
            )
            DMontadorEstruturaPai montadorEstruturaPai) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.montarEstrutura(montadorEstruturaPai));
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ANALYST, ROLE_OPERATOR')")
    @PostMapping(value = "/estruturamodulacao")
    public ResponseEntity<?> criarEstruturaModulacao(
            @RequestBody
            @Schema(
                    description = "Cadastro e criação de estrutura de um Pai",
                    requiredProperties = "descricao",
                    example = """
                    {
                        "descricao": "Descrição"
                    }
                    """
            )
            DMontadorEstruturaPaiModulacao montadorEstruturaPaiModulacao) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.montarEstruturaModulacao(montadorEstruturaPaiModulacao));
    }

    /**
     * @param dto representa o objeto Pai a ser atualizado
     */
    @Operation(summary = "Atualizar um Pai", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "409", description = "Conflict"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity")
    })
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ANALYST, ROLE_OPERATOR')")
    @PutMapping
    public ResponseEntity<?> atualizar(
            @RequestBody
            @Schema(
                    description = "Objeto de Pai para edição",
                    requiredProperties = "codigo, descrição",
                    example = """
                    {
                        "codigo": 1,
                        "modelo": {
                            "codigo": 1
                        },
                        "categoriaComponente": {
                            "codigo": 1
                        },
                        "descricao": "Descrição",
                        "bordasComprimento": 2,
                        "bordasLargura": 2,
                        "numeroCantoneiras": 4,
                        "tntUmaFace": true,
                        "plasticoAcima": true,
                        "plasticoAdicional": 100.0,
                        "larguraPlastico": 100
                    }
                    """
            )
            DPai dto){
        return ResponseEntity.ok(service.atualizar(dto));
    }

    /**
     * @param codigos lista dos códigos dos Paies a serem inativados
     */
    @Operation(summary = "Inativar uma lista de Pais", method = "PATCH", description = "A situação dos objetos é alterada para 'Inativo'")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "206", description = "Partial content"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ANALYST, ROLE_OPERATOR')")
    @PatchMapping(value = "/inativar")
    public ResponseEntity<?> inativar(@RequestParam("codigo") List<Integer> codigos) {
        List<Integer> alteradosComSucesso = new ArrayList<>();
        List<Map<String, Object>> falhas = new ArrayList<>();

        codigos.forEach(codigo -> {
            try {
                service.inativar(codigo);
                alteradosComSucesso.add(codigo);
            } catch (Exception e) {
                Map<String, Object> erro = new HashMap<>();
                erro.put("codigo", codigo);
                erro.put("mensagem", e.getMessage());
                falhas.add(erro);
            }
        });

        Map<String, Object> resposta = Map.of(
                "alteradosComSucesso", alteradosComSucesso,
                "falhas", falhas
        );

        return falhas.isEmpty() ? ResponseEntity.ok(resposta) : ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(resposta);
    }

    /**
     * @param codigos lista dos códigos dos Paies a serem excluídos
     */
    @Operation(summary = "Excluir uma lista de Pais", method = "DELETE", description = "A situação dos objetos é alterada para 'Lixeira'")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "206", description = "Partial content"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ANALYST, ROLE_OPERATOR')")
    @DeleteMapping
    public ResponseEntity<?> remover(@RequestParam("codigo") List<Integer> codigos){
        List<Integer> alteradosComSucesso = new ArrayList<>();
        List<Map<String, Object>> falhas = new ArrayList<>();

        codigos.forEach(codigo -> {
            try {
                service.excluir(codigo);
                alteradosComSucesso.add(codigo);
            } catch (Exception e) {
                Map<String, Object> erro = new HashMap<>();
                erro.put("codigo", codigo);
                erro.put("mensagem", e.getMessage());
                falhas.add(erro);
            }
        });

        Map<String, Object> resposta = Map.of(
                "alteradosComSucesso", alteradosComSucesso,
                "falhas", falhas
        );

        return falhas.isEmpty() ? ResponseEntity.ok(resposta) : ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(resposta);
    }
}
