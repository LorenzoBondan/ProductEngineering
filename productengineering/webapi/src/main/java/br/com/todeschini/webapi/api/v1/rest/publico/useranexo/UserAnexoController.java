package br.com.todeschini.webapi.api.v1.rest.publico.useranexo;

import br.com.todeschini.domain.PageableRequest;
import br.com.todeschini.domain.business.auth.authservice.api.AuthService;
import br.com.todeschini.domain.business.publico.anexo.DAnexo;
import br.com.todeschini.domain.business.publico.anexo.api.AnexoService;
import br.com.todeschini.domain.business.publico.binario.DBinario;
import br.com.todeschini.domain.business.publico.binario.api.BinarioService;
import br.com.todeschini.domain.business.publico.user.api.UserService;
import br.com.todeschini.domain.business.publico.useranexo.DUserAnexo;
import br.com.todeschini.domain.business.publico.useranexo.api.UserAnexoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Tag(name = "UserAnexo")
@RestController
@RequestMapping("/api/useranexo")
public class UserAnexoController {

    private final UserAnexoService service;
    private final UserService userService;
    private final AnexoService anexoService;
    private final BinarioService binarioService;
    private final AuthService authService;

    public UserAnexoController(UserAnexoService service, UserService userService, AnexoService anexoService, BinarioService binarioService, AuthService authService) {
        this.service = service;
        this.userService = userService;
        this.anexoService = anexoService;
        this.binarioService = binarioService;
        this.authService = authService;
    }

    /**
     * @param colunas representam as colunas da tabela a serem pesquisadas
     * @param operacoes representam se as comparações serão de igualdade, maior que, menor que ou diferente de
     * @param valores representam os valores a serem usados como parâmetros de busca
     * @param page atributo da paginação, representa o número da página da busca
     * @param pageSize atributo da paginação, representa o total de elementos em uma página
     * @param sort atributo da paginação, representa a ordenação
     */
    @Operation(summary = "Pesquisar todas as UserAnexos por colunas, operações e valores", method = "GET")
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
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
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
                        .colunas(colunas)
                        .operacoes(operacoes)
                        .valores(valores)
                        .columnMap(Map.of(
                                "codigo", "cduserAnexo",
                                "situacao", "situacao"))
                        .build())
        );
    }

    /**
     * @param id representa o ID da UserAnexo a ser pesquisada
     */
    @Operation(summary = "Pesquisar uma UserAnexo por id", method = "GET", description = "Pesquisa um objeto por id, independente da sua situação")
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
     * @param codigo representa o ID da UserAnexo a ser pesquisada
     */
    @Operation(summary = "Pesquisar uma lista de versões de uma UserAnexo por id", method = "GET", description = "Pesquisa uma lista de versões por id, independente da sua situação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ANALYST', 'ROLE_OPERATOR')")
    @GetMapping(value = "/historico")
    public ResponseEntity<?> pesquisarHistorico(@RequestParam("codigo") Integer codigo){
        authService.validateSelfOrAdmin(Long.valueOf(codigo));
        return ResponseEntity.ok(service.buscarHistorico(codigo));
    }

    /**
     * @param codigo   O código do Usuário a qual o anexo será vinculado. Representado por um número inteiro.
     * @param file     O arquivo a ser anexado (ex.: documento PDF, imagem), enviado no formato multipart.
     * @param nome     O nome do anexo. Ex.: "documento.pdf".
     * @param mimeType O tipo MIME do anexo. Ex.: "application/pdf" para PDFs.
     */
    @Operation(summary = "Inserir um Anexo de Usuário", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity")
    })
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ANALYST', 'ROLE_OPERATOR')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> criar(
            @RequestParam("codigo") Integer codigo,
            @RequestParam("file") MultipartFile file, // Recebe o arquivo
            @RequestParam("nome") String nome,
            @RequestParam("mimeType") String mimeType) {

        authService.validateSelfOrAdmin(Long.valueOf(codigo));

        // Converter MultipartFile para byte[]
        byte[] bytes;
        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Erro ao processar o arquivo");
        }

        // Criar as entidades com os dados do anexo
        DBinario binario = binarioService.incluir(new DBinario(null, bytes, null));
        DAnexo anexo = anexoService.incluir(
                new DAnexo(
                        null, binario, nome, mimeType, null
                )
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(service.incluir(
                new DUserAnexo(
                        null, userService.buscar(codigo), anexo, null
                ))
        );
    }

    /**
     * @param codigo    Código do anexo de Usuário que será atualizado.
     * @param file      (Opcional) O arquivo a ser atualizado, enviado no formato multipart.
     * @param nome      O nome do anexo. Ex.: "documento.pdf".
     * @param mimeType  O tipo MIME do anexo. Ex.: "application/pdf".
     */
    @Operation(summary = "Atualizar um Anexo de Usuário", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity")
    })
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ANALYST', 'ROLE_OPERATOR')")
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> atualizar(
            @RequestParam("codigo") Integer codigo,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam("nome") String nome,
            @RequestParam("mimeType") String mimeType) {

        authService.validateSelfOrAdmin(Long.valueOf(codigo));

        // Atualizar o binário, se o arquivo for enviado
        byte[] bytes = null;
        if (file != null && !file.isEmpty()) {
            try {
                bytes = file.getBytes();
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Erro ao processar o arquivo");
            }
        }

        // Buscar o anexo existente
        DUserAnexo userAnexo = service.buscar(codigo);

        // Atualizar o binário, se o arquivo foi enviado
        if (bytes != null) {
            DBinario binario = userAnexo.getAnexo().getBinario();
            binario.setBytes(bytes);
            binarioService.atualizar(binario);
        }

        // Atualizar os outros dados do anexo
        DAnexo anexo = userAnexo.getAnexo();
        anexo.setNome(nome);
        anexo.setMimeType(mimeType);
        anexoService.atualizar(anexo);

        return ResponseEntity.status(HttpStatus.OK).body(service.atualizar(userAnexo));
    }

    /**
     * @param codigos lista dos códigos das UserAnexos a serem inativadas
     */
    @Operation(summary = "Inativar uma lista de UserAnexos", method = "PATCH", description = "A situação dos objetos é alterada para 'Inativo'")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "206", description = "Partial content"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ANALYST')")
    @PatchMapping(value = "/inativar")
    public ResponseEntity<?> inativar(@RequestParam("codigo") List<Integer> codigos) {
        List<Integer> alteradosComSucesso = new ArrayList<>(), falhas = new ArrayList<>();

        codigos.forEach(codigo -> {
            try {
                authService.validateSelfOrAdmin(Long.valueOf(codigo));
                service.inativar(codigo);
                alteradosComSucesso.add(codigo);
            } catch (Exception e) {
                falhas.add(codigo);
            }
        });

        Map<String, List<Integer>> resposta = Map.of(
                "alteradosComSucesso", alteradosComSucesso,
                "falhas", falhas
        );

        return falhas.isEmpty() ? ResponseEntity.ok(resposta) : ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(resposta);
    }

    /**
     * @param codigos lista dos códigos das UserAnexos a serem excluídas
     */
    @Operation(summary = "Excluir uma lista de UserAnexos", method = "DELETE", description = "A situação dos objetos é alterada para 'Lixeira'")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok"),
            @ApiResponse(responseCode = "206", description = "Partial content"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ANALYST')")
    @DeleteMapping
    public ResponseEntity<?> remover(@RequestParam("codigo") List<Integer> codigos){
        List<Integer> excluidosComSucesso = new ArrayList<>(), falhas = new ArrayList<>();

        codigos.forEach(codigo -> {
            try {
                authService.validateSelfOrAdmin(Long.valueOf(codigo));
                service.excluir(codigo);
                excluidosComSucesso.add(codigo);
            } catch (Exception e) {
                falhas.add(codigo);
            }
        });

        Map<String, List<Integer>> resposta = Map.of(
                "excluidosComSucesso", excluidosComSucesso,
                "falhas", falhas
        );

        return falhas.isEmpty() ? ResponseEntity.ok(resposta) : ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(resposta);
    }
}
