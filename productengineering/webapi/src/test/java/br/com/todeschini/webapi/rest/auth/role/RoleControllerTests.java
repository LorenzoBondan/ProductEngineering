package br.com.todeschini.webapi.rest.auth.role;

import br.com.todeschini.domain.business.auth.role.DRole;
import br.com.todeschini.domain.business.auth.role.RoleServiceImpl;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.auth.role.CrudRoleImpl;
import br.com.todeschini.webapi.UserTest;
import br.com.todeschini.webapi.rest.auth.TokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RoleControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CrudRoleImpl crud;

    @Autowired
    private TokenUtil tokenUtil;

    @Value("${todeschini.users.admin.email}")
    private String adminEmail;
    @Value("${todeschini.users.admin.password}")
    private String adminPassword;

    private DRole object, nonExistingObject;
    private Long existingId, nonExistingId;
    private String accessToken;
    private final Pageable pageable = PageRequest.of(0, 10);

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 99999999L;

        accessToken = tokenUtil.obtainAccessToken(mockMvc, new UserTest(adminEmail, adminPassword));

        object = RoleFactory.createDRole();
        nonExistingObject = new DRole(nonExistingId);

        // findAll
        //when(repository.findByAuthorityStartingWithIgnoreCase(any(), any(), any()))
                //.thenReturn(Page.empty());

        // findById
        when(crud.find(existingId)).thenReturn(object);
        when(crud.find(nonExistingId)).thenThrow(ResourceNotFoundException.class);

        // insert
        when(crud.insert(any(DRole.class))).thenReturn(object);

        // update
        when(crud.update(eq(existingId), any())).thenReturn(object);
        when(crud.update(eq(nonExistingId), any())).thenThrow(ResourceNotFoundException.class);

        // delete
        doNothing().when(crud).delete(existingId);
        doThrow(ResourceNotFoundException.class).when(crud).delete(nonExistingId);
    }

    /*
    @Test
    void findByStatusInAndNameContainingIgnoreCaseShouldReturnPage() {
        // Arrange

        // Act
        ResponseEntity<?> responseEntity = controller.findByAuthorityStartingWithIgnoreCase(description, pageable);

        // Assert
        assertNotNull(responseEntity.getBody());
        assertTrue(responseEntity.getBody() instanceof Page);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        Page<?> responseBody = (Page<?>) responseEntity.getBody();
        boolean allMatch = responseBody.stream().allMatch(obj -> obj instanceof RoleDTO);
        assertThat(allMatch).isTrue();

        verify(repository, times(1)).findByAuthorityStartingWithIgnoreCase(
                eq(description), eq(pageable), eq(RoleDTO.class));
    }

     */

    @Test
    public void findByIdShouldReturnObjectWhenIdExists() throws Exception {
        mockMvc.perform(get("/roles/{id}",existingId)
                        .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.authority").exists());
    }
/*
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
        assertThat(responseEntity.getBody()).isInstanceOf(DRole.class);
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


 */
}
