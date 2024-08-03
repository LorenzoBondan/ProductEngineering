package br.com.todeschini.webapi.rest.auth.role;

import br.com.todeschini.domain.business.auth.role.DRole;
import br.com.todeschini.persistence.auth.role.CrudRoleImpl;
import br.com.todeschini.persistence.auth.role.RoleRepository;
import br.com.todeschini.webapi.ApiTestUtil;
import br.com.todeschini.webapi.BaseControllerIT;
import br.com.todeschini.webapi.ValidationConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;

import javax.transaction.Transactional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class RoleControllerIT extends BaseControllerIT<DRole> {

    @MockBean
    private CrudRoleImpl crud;
    @MockBean
    private RoleRepository repository;

    @BeforeEach
    void setUp() throws Exception {
        setConfiguration("/roles");

        factoryObject = RoleFactory.createDRole();
        duplicatedObject = RoleFactory.createDuplicatedDRole();
        nonExistingObject = RoleFactory.createNonExistingDRole(nonExistingId);

        setCrudBehavior(factoryObject, nonExistingObject, crud);

        // findAll
        when(repository.findByAuthorityStartingWithIgnoreCase(anyString(), any(), any()))
                .thenReturn(Page.empty());
    }

    @Test
    public void findByAuthorityStartingWithIgnoreCaseShouldReturnPage() throws Exception {
        ApiTestUtil.performRequest(mockMvc, HttpMethod.GET, baseUrl + "?authority=", statusListBody, adminAccessToken, status().isOk());
        ApiTestUtil.performRequest(mockMvc, HttpMethod.GET, baseUrl + "?authority=", statusListBody, readOnlyAccessToken, status().isForbidden());
    }

    @Test
    public void findShouldReturnObjectTest() throws Exception {
        ApiTestUtil.performRequest(mockMvc, HttpMethod.GET, baseUrl + "/" + existingId, "{}", adminAccessToken, status().isOk());
        ApiTestUtil.performRequest(mockMvc, HttpMethod.GET, baseUrl + "/" + existingId, "{}", readOnlyAccessToken, status().isForbidden());
    }

    @Test
    public void findShouldThrowResourceNotFoundExceptionWhenIdDoesNotExistsTest() throws Exception {
        ApiTestUtil.performRequest(mockMvc, HttpMethod.GET, baseUrl + "/" + nonExistingId, "{}", adminAccessToken, status().isNotFound());
        ApiTestUtil.performRequest(mockMvc, HttpMethod.GET, baseUrl + "/" + nonExistingId, "{}", readOnlyAccessToken, status().isForbidden());
    }

    @Test
    public void insertShouldReturnObjectTest() throws Exception {
        insertShouldReturnObject();
    }

    @Test
    public void insertShouldThrowDuplicatedResourceExceptionWhenObjectHasDuplicatedDescriptionTest() throws Exception {
        insertShouldThrowDuplicatedResourceExceptionWhenObjectHasDuplicatedDescription();
    }

    @Test
    public void updateShouldReturnOkWhenValidDataTest() throws Exception {
        updateShouldReturnOkWhenValidData();
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenObjectIdDoesNotExistsTest() throws Exception {
        updateShouldThrowResourceNotFoundExceptionWhenObjectIdDoesNotExists();
    }

    @Test
    public void updateShouldThrowDuplicatedResourceExceptionWhenObjectHasDuplicatedDescriptionTest() throws Exception {
        updateShouldThrowDuplicatedResourceExceptionWhenObjectHasDuplicatedDescription();
    }

    @Test
    public void deleteShouldReturnNoContentWhenIdExistsTest() throws Exception {
        deleteShouldReturnNoContentWhenIdExists();
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExistsTest() throws Exception {
        deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists();
    }

    @Test
    public void validationShouldThrowUnprocessableEntityWhenInvalidDataTest() throws Exception {
        factoryObject = RoleFactory.createDRole();
        factoryObject.setAuthority(null);
        validate(factoryObject);

        factoryObject = RoleFactory.createDRole();
        factoryObject.setAuthority(ValidationConstants.blank);
        validate(factoryObject);

        factoryObject = RoleFactory.createDRole();
        factoryObject.setAuthority(ValidationConstants.smallerSize);
        validate(factoryObject);

        factoryObject = RoleFactory.createDRole();
        factoryObject.setAuthority(ValidationConstants.biggerSize);
        validate(factoryObject);
    }
}
