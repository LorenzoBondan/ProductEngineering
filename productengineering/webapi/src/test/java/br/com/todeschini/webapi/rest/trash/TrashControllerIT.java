package br.com.todeschini.webapi.rest.trash;

import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.trash.Trash;
import br.com.todeschini.persistence.trash.TrashRepository;
import br.com.todeschini.persistence.util.EntityService;
import br.com.todeschini.webapi.ApiTestUtil;
import br.com.todeschini.webapi.BaseControllerIT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class TrashControllerIT extends BaseControllerIT<Trash> {

    @MockBean
    private TrashRepository repository;
    @MockBean
    private EntityService entityService;

    @BeforeEach
    void setUp() throws Exception {
        setConfiguration("/trash");

        // findAll
        when(repository.findByUserAndDateRangeAndTable(anyString(), any(LocalDateTime.class), any(LocalDateTime.class), anyString()))
                .thenReturn(new ArrayList<>());

        // retrieve
        doNothing().when(entityService).retrieve(eq(existingId), anyBoolean());
        doThrow(ResourceNotFoundException.class).when(entityService).retrieve(eq(nonExistingId), anyBoolean());
    }

    @Test
    public void findByUserAndDateRangeAndTableShouldReturnList() throws Exception {
        ApiTestUtil.performRequest(mockMvc, HttpMethod.GET, baseUrl + "?username=&startDate=&endDate=&table=", "{}", adminAccessToken, status().isOk());
    }

    @Test
    public void retrieveShouldReturnOkWhenIdExists() throws Exception {
        ApiTestUtil.performRequest(mockMvc, HttpMethod.GET, baseUrl + "/retrieve/" + existingId + "?retrieveDependencies=true", "{}", adminAccessToken, status().isOk());
    }

    @Test
    public void retrieveShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() throws Exception {
        ApiTestUtil.performRequest(mockMvc, HttpMethod.GET, baseUrl + "/retrieve/" + nonExistingId + "?retrieveDependencies=true", "{}", adminAccessToken, status().isNotFound());
    }
}
