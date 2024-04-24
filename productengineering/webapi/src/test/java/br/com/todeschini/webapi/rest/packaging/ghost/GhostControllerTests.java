package br.com.todeschini.webapi.rest.packaging.ghost;

import br.com.todeschini.domain.business.packaging.ghost.DGhost;
import br.com.todeschini.domain.business.packaging.ghost.api.GhostService;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.enums.Status;
import br.com.todeschini.persistence.packaging.ghost.GhostRepository;
import br.com.todeschini.webapi.api.v1.rest.packaging.ghost.GhostController;
import br.com.todeschini.webapi.api.v1.rest.packaging.ghost.projection.GhostDTO;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@Transactional
public class GhostControllerTests {

    private final GhostService service = mock(GhostService.class);
    private final GhostRepository repository = mock(GhostRepository.class);
    private final GhostController controller = new GhostController(service, repository);

    private DGhost object, nonExistingObject;
    private String existingId, nonExistingId;
    private String description;
    private final List<Status> statusList = new ArrayList<>();
    private final List<DGhost> objectList = new ArrayList<>();
    private final Pageable pageable = PageRequest.of(0, 10);

    @BeforeEach
    void setUp() {
        existingId = "1L";
        nonExistingId = "99999999L";

        description = "description";

        statusList.add(Status.ACTIVE);

        object = new DGhost(existingId);
        nonExistingObject = new DGhost(nonExistingId);

        // findAll
        when(repository.findByStatusInAndDescriptionContainingIgnoreCase(anyList(), anyString(), any(), any()))
                .thenReturn(Page.empty());

        // findAllAndCurrent
        when(service.findAllActiveAndCurrentOne(existingId)).thenReturn(objectList);
        when(service.findAllActiveAndCurrentOne(nonExistingId)).thenReturn(objectList);
        when(service.findAllActiveAndCurrentOne(null)).thenReturn(objectList);

        // findById
        when(service.find(anyString())).thenReturn(object);
        doThrow(ResourceNotFoundException.class).when(service).find(nonExistingId);

        // insert
        when(service.insert(any(DGhost.class))).thenReturn(object);

        // update
        when(service.update(eq(existingId), any(DGhost.class))).thenReturn(object);
        doThrow(ResourceNotFoundException.class).when(service).update(nonExistingId, nonExistingObject);

        // delete
        doNothing().when(service).delete(existingId);
        doThrow(ResourceNotFoundException.class).when(service).delete(nonExistingId);

        // inactivate
        doNothing().when(service).inactivate(existingId);
        doThrow(ResourceNotFoundException.class).when(service).inactivate(nonExistingId);
    }

    @Test
    void findByStatusInAndNameContainingIgnoreCaseShouldReturnPage() {
        // Arrange

        // Act
        ResponseEntity<?> responseEntity = controller.findByStatusInAndDescriptionContainingIgnoreCase("ACTIVE", description, pageable);

        // Assert
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody() instanceof Page);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        Page<?> responseBody = (Page<?>) responseEntity.getBody();
        boolean allMatch = responseBody.stream().allMatch(obj -> obj instanceof GhostDTO);
        assertThat(allMatch).isTrue();

        verify(repository, times(1)).findByStatusInAndDescriptionContainingIgnoreCase(eq(statusList),
                eq(description), eq(pageable), eq(GhostDTO.class));
    }

    @Test
    void findAllActiveAndCurrentOneShouldReturnListWhenIdExists(){
        // Executando o método
        ResponseEntity<?> responseEntity = controller.findAllActiveAndCurrentOne(existingId);

        // Verificando o tipo de dado e código de status
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isInstanceOf(List.class);
        List<?> responseBody = (List<?>) responseEntity.getBody();
        assert responseBody != null;
        boolean allMatch = responseBody.stream().allMatch(obj -> obj instanceof GhostDTO);
        assertThat(allMatch).isTrue();

        // Verificando se o serviço foi chamado corretamente
        verify(service, times(1)).findAllActiveAndCurrentOne(eq(existingId));
    }

    @Test
    void findAllActiveAndCurrentOneShouldReturnListWhenIdDoesNotExists(){
        // Executando o método
        ResponseEntity<?> responseEntity = controller.findAllActiveAndCurrentOne(nonExistingId);

        // Verificando o tipo de dado e código de status
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isInstanceOf(List.class);
        List<?> responseBody = (List<?>) responseEntity.getBody();
        assert responseBody != null;
        boolean allMatch = responseBody.stream().allMatch(obj -> obj instanceof GhostDTO);
        assertThat(allMatch).isTrue();

        // Verificando se o serviço foi chamado corretamente
        verify(service, times(1)).findAllActiveAndCurrentOne(eq(nonExistingId));
    }

    @Test
    void findAllActiveAndCurrentOneShouldReturnListWhenIdIsNull(){
        // Executando o método
        ResponseEntity<?> responseEntity = controller.findAllActiveAndCurrentOne(null);

        // Verificando o tipo de dado e código de status
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isInstanceOf(List.class);
        List<?> responseBody = (List<?>) responseEntity.getBody();
        assert responseBody != null;
        boolean allMatch = responseBody.stream().allMatch(obj -> obj instanceof GhostDTO);
        assertThat(allMatch).isTrue();

        // Verificando se o serviço foi chamado corretamente
        verify(service, times(1)).findAllActiveAndCurrentOne(eq(null));
    }

    @Test
    public void findByIdShouldReturnObjectWhenIdExists() {
        // Executando o método
        ResponseEntity<?> responseEntity = controller.findById(existingId);

        // Verificando o tipo de dado e código de status
        assertThat(responseEntity.getBody()).isInstanceOf(DGhost.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verificando se o serviço foi chamado corretamente
        verify(service).find(existingId);
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        // Executando o método
        assertThrows(ResourceNotFoundException.class, () -> {
            ResponseEntity<?> responseEntity = controller.findById(nonExistingId);
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        });

        // Verificando se o serviço foi chamado corretamente
        verify(service).find(eq(nonExistingId));
    }

    @Test
    public void insertShouldReturnObjectCreated() {
        // Criando um MockHttpServletRequest e configurando-o como a requisição atual
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        // Executando o método
        ResponseEntity<?> responseEntity = controller.insert(object);

        // Verificando o tipo de dado e código de status
        assertThat(responseEntity.getBody()).isEqualTo(object);
        assertThat(responseEntity.getBody()).isInstanceOf(DGhost.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        // Verificando se o serviço foi chamado corretamente
        verify(service).insert(eq(object));
    }


    @Test
    public void updateShouldReturnOkWhenObjectExists() {
        // Executando o método
        ResponseEntity<?> responseEntity = controller.update(existingId, object);

        // Verificando o código de status
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verificando se o serviço foi chamado corretamente
        verify(service).update(eq(existingId), eq(object));
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenObjectDoesNotExists() {
        // Executando o método
        assertThrows(ResourceNotFoundException.class, () -> {
            ResponseEntity<?> responseEntity = controller.update(nonExistingId, nonExistingObject);
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        });

        // Verificando se o serviço foi chamado corretamente
        verify(service).update(eq(nonExistingId), eq(nonExistingObject));
    }

    @Test
    public void deleteShouldReturnNoContentWhenIdExists() {
        // Executando o método
        ResponseEntity<?> responseEntity = controller.delete(existingId);

        // Verificando o código de status
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        // Verificando se o serviço foi chamado corretamente
        verify(service).delete(eq(existingId));
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        // Executando o método
        assertThrows(ResourceNotFoundException.class, () -> {
            ResponseEntity<?> responseEntity = controller.delete(nonExistingId);
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        });

        // Verificando se o serviço foi chamado corretamente
        verify(service).delete(eq(nonExistingId));
    }

    @Test
    public void inactivateShouldReturnOkWhenIdExists() {
        // Executando o método
        ResponseEntity<?> responseEntity = controller.inactivate(existingId);

        // Verificando o código de status
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verificando se o serviço foi chamado corretamente
        verify(service).inactivate(eq(existingId));
    }

    @Test
    public void inactivateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        // Executando o método
        assertThrows(ResourceNotFoundException.class, () -> {
            ResponseEntity<?> responseEntity = controller.inactivate(nonExistingId);
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        });

        // Verificando se o serviço foi chamado corretamente
        verify(service).inactivate(eq(nonExistingId));
    }
}
