package br.com.todeschini.webapi.rest.trash;

import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.trash.Trash;
import br.com.todeschini.persistence.trash.TrashRepository;
import br.com.todeschini.persistence.util.EntityService;
import br.com.todeschini.webapi.api.v1.rest.trash.TrashController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@Transactional
public class TrashControllerTests {

    private final EntityService service = mock(EntityService.class);
    private final TrashRepository repository = mock(TrashRepository.class);
    private final TrashController controller = new TrashController(repository, service);

    private Long existingId, nonExistingId;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 99999999L;

        // findAll
        when(repository.findByUserAndDateRangeAndTable(anyString(), any(LocalDateTime.class), any(LocalDateTime.class), anyString()))
                .thenReturn(new ArrayList<>());

        // retrieve
        doNothing().when(service).retrieve(eq(existingId), anyBoolean());
        doThrow(ResourceNotFoundException.class).when(service).retrieve(eq(nonExistingId), anyBoolean());
    }

    @Test
    void findByUserAndDateRangeAndTableShouldReturnPage() {
        // Arrange
        String username = "example";
        LocalDateTime startDate = LocalDateTime.of(2024, Month.JANUARY, 1, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, Month.JANUARY, 31, 23, 59, 59);
        String table = "exampleTable";

        // Mock para retornar uma lista vazia
        List<Trash> emptyTrashList = Collections.emptyList();
        when(repository.findByUserAndDateRangeAndTable(eq(username), eq(startDate), eq(endDate), eq(table)))
                .thenReturn(emptyTrashList);

        // Act
        ResponseEntity<?> responseEntity = controller.findByUserAndDateRangeAndTable(username, startDate, endDate, table);

        // Assert
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody() instanceof List);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        List<Trash> responseBody = (List<Trash>) responseEntity.getBody();
        assertThat(responseBody).isEqualTo(emptyTrashList);

        // Verificar se o método do repositório foi chamado corretamente
        verify(repository, times(1)).findByUserAndDateRangeAndTable(eq(username), eq(startDate), eq(endDate), eq(table));
    }

    @Test
    public void retrieveShouldReturnOkWhenIdExists() {
        // Executando o método
        ResponseEntity<?> responseEntity = controller.retrieve(existingId, true);

        // Verificando o código de status
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verificando se o serviço foi chamado corretamente
        verify(service).retrieve(eq(existingId), eq(true));
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
        // Executando o método
        assertThrows(ResourceNotFoundException.class, () -> {
            ResponseEntity<?> responseEntity = controller.retrieve(nonExistingId, true);
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        });

        // Verificando se o serviço foi chamado corretamente
        verify(service).retrieve(eq(nonExistingId), eq(true));
    }
}
