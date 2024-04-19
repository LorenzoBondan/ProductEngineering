package br.com.todeschini.webapi.rest.packaging.ghost;

import br.com.todeschini.domain.business.packaging.ghost.DGhost;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.packaging.ghost.CrudGhostImpl;
import br.com.todeschini.persistence.packaging.ghost.GhostRepository;
import br.com.todeschini.webapi.ApiTestUtil;
import br.com.todeschini.webapi.BaseControllerIT;
import br.com.todeschini.webapi.rest.publico.son.SonFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;

import javax.transaction.Transactional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class GhostControllerIT extends BaseControllerIT<DGhost> {

    @MockBean
    private CrudGhostImpl crud;
    @MockBean
    private GhostRepository repository;

    public String existingCode = "220000F99", nonExistingCode = "999999F99";

    @BeforeEach
    void setUp() throws Exception {
        setConfiguration("/ghosts");

        factoryObject = GhostFactory.createDGhost();
        duplicatedObject = GhostFactory.createDuplicatedDGhost();
        nonExistingObject = GhostFactory.createNonExistingDGhost(nonExistingCode);

        // findAllAndCurrent
        when(crud.findAllActiveAndCurrentOne(existingCode)).thenReturn(objectList);
        when(crud.findAllActiveAndCurrentOne(nonExistingCode)).thenReturn(objectList);
        when(crud.findAllActiveAndCurrentOne(null)).thenReturn(objectList);

        // findById
        when(crud.find(existingCode)).thenReturn(factoryObject);
        doThrow(ResourceNotFoundException.class).when(crud).find(nonExistingCode);

        // insert
        when(crud.insert(any())).thenReturn(factoryObject);
        doThrow(DuplicatedResourceException.class).when(crud).insert(duplicatedObject);

        // update
        when(crud.update(existingCode, factoryObject)).thenReturn(factoryObject);
        doThrow(ResourceNotFoundException.class).when(crud).update(nonExistingCode, nonExistingObject);
        doThrow(DuplicatedResourceException.class).when(crud).update(existingCode, duplicatedObject);

        // delete
        doNothing().when(crud).delete(existingCode);
        doThrow(ResourceNotFoundException.class).when(crud).delete(nonExistingCode);

        // inactivate
        doNothing().when(crud).inactivate(existingCode);
        doThrow(ResourceNotFoundException.class).when(crud).inactivate(nonExistingCode);

        // findAll
        when(repository.findByStatusInAndDescriptionContainingIgnoreCase(anyList(), anyString(), any(), any()))
                .thenReturn(Page.empty());

    }

    @Test
    public void pesquisarTodosPorDescricaoEListaDeSituacoesShouldReturnPage() throws Exception {
        ApiTestUtil.performRequest(mockMvc, HttpMethod.GET, baseUrl + "?description=", statusListBody, adminAccessToken, status().isOk());
        ApiTestUtil.performRequest(mockMvc, HttpMethod.GET, baseUrl + "?description=", statusListBody, readOnlyAccessToken, status().isOk());
    }

    @Test
    public void findAllActiveAndCurrentOneShouldReturnListTest() throws Exception {
        findAllActiveAndCurrentOneShouldReturnList();
    }

    @Test
    public void findShouldReturnObjectTest() throws Exception {
        findShouldReturnObject();
    }

    @Test
    public void findShouldThrowResourceNotFoundExceptionWhenIdDoesNotExistsTest() throws Exception {
        ApiTestUtil.performRequest(mockMvc, HttpMethod.GET, baseUrl + "/" + nonExistingCode, "{}", adminAccessToken, status().isNotFound());
        ApiTestUtil.performRequest(mockMvc, HttpMethod.GET, baseUrl + "/" + nonExistingCode, "{}", readOnlyAccessToken, status().isNotFound());
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
        String jsonBody = objectMapper.writeValueAsString(nonExistingObject);
        ApiTestUtil.performRequest(mockMvc, HttpMethod.PUT, baseUrl + "/" + nonExistingCode, jsonBody, adminAccessToken, status().isNotFound());
    }

    @Test
    public void updateShouldThrowDuplicatedResourceExceptionWhenObjectHasDuplicatedDescriptionTest() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(duplicatedObject);
        ApiTestUtil.performRequest(mockMvc, HttpMethod.PUT, baseUrl + "/" + existingCode, jsonBody, adminAccessToken,status().isConflict());
    }

    @Test
    public void inactivateShouldReturnOkWhenIdExistsTest() throws Exception {
        inactivateShouldReturnOkWhenIdExists();
    }

    @Test
    public void inactivateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExistsTest() throws Exception {
        ApiTestUtil.performRequest(mockMvc, HttpMethod.PUT, baseUrl + "/inactivate/" + nonExistingCode, "{}", adminAccessToken, status().isNotFound());
    }

    @Test
    public void deleteShouldReturnNoContentWhenIdExistsTest() throws Exception {
        deleteShouldReturnNoContentWhenIdExists();
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExistsTest() throws Exception {
        ApiTestUtil.performRequest(mockMvc, HttpMethod.DELETE, baseUrl + "/" + nonExistingCode, "{}", adminAccessToken, status().isNotFound());
    }

    @Test
    public void validationShouldThrowUnprocessableEntityWhenInvalidDataTest() throws Exception {
        String blank = "", smallerSize = "a", biggerSize = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        Integer negative = -1;

        factoryObject = GhostFactory.createDGhost();
        factoryObject.setCode(null);
        validate(factoryObject);

        factoryObject = GhostFactory.createDGhost();
        factoryObject.setCode("1");
        validate(factoryObject);

        factoryObject = GhostFactory.createDGhost();
        factoryObject.setCode("1111111111111111111L");
        validate(factoryObject);

        factoryObject = GhostFactory.createDGhost();
        factoryObject.setDescription(null);
        validate(factoryObject);

        factoryObject = GhostFactory.createDGhost();
        factoryObject.setDescription(blank);
        validate(factoryObject);

        factoryObject = GhostFactory.createDGhost();
        factoryObject.setDescription(smallerSize);
        validate(factoryObject);

        factoryObject = GhostFactory.createDGhost();
        factoryObject.setDescription(biggerSize);
        validate(factoryObject);

        factoryObject = GhostFactory.createDGhost();
        factoryObject.setMeasure1(negative);
        validate(factoryObject);

        factoryObject = GhostFactory.createDGhost();
        factoryObject.setMeasure2(negative);
        validate(factoryObject);

        factoryObject = GhostFactory.createDGhost();
        factoryObject.setMeasure3(negative);
        validate(factoryObject);
    }
}
