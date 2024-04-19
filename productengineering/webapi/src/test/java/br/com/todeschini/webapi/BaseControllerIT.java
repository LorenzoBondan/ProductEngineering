package br.com.todeschini.webapi;

import br.com.todeschini.domain.SimpleCrud;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.persistence.entities.enums.Status;
import br.com.todeschini.webapi.rest.auth.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class BaseControllerIT<D> {

    @Value("${todeschini.users.admin.email}")
    private String adminEmail;
    @Value("${todeschini.users.admin.password}")
    private String adminPassword;
    @Value("${todeschini.users.readOnly.email}")
    private String readOnlyEmail;
    @Value("${todeschini.users.readOnly.password}")
    private String readOnlyPassword;

    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;

    protected String baseUrl;

    protected static final Long existingId = 1L, nonExistingId = 999999999L;
    protected String statusListBody, adminAccessToken, readOnlyAccessToken;
    protected D factoryObject, duplicatedObject, nonExistingObject;
    protected final List<D> objectList = new ArrayList<>();
    protected final List<Status> statusList = new ArrayList<>();

    public void setConfiguration(String baseUrl) throws Exception { // define configurações de autenticação e situação
        this.baseUrl = baseUrl;
        statusList.add(Status.ACTIVE);
        statusListBody = new ObjectMapper().writeValueAsString(statusList);

        adminAccessToken = tokenUtil.obtainAccessToken(mockMvc, new UserTest(adminEmail, adminPassword));
        readOnlyAccessToken = tokenUtil.obtainAccessToken(mockMvc, new UserTest(readOnlyEmail, readOnlyPassword));
    }

    public void setCrudBehavior(D factoryObject, D nonExistingObject, SimpleCrud<D, Long> crud){ // define o comportamento dos métodos do CRUD
        // findAllAndCurrent
        when(crud.findAllActiveAndCurrentOne(existingId)).thenReturn(objectList);
        when(crud.findAllActiveAndCurrentOne(nonExistingId)).thenReturn(objectList);
        when(crud.findAllActiveAndCurrentOne(null)).thenReturn(objectList);

        // findById
        when(crud.find(existingId)).thenReturn(factoryObject);
        doThrow(ResourceNotFoundException.class).when(crud).find(nonExistingId);

        // insert
        when(crud.insert(any())).thenReturn(factoryObject);
        doThrow(DuplicatedResourceException.class).when(crud).insert(duplicatedObject);

        // update
        when(crud.update(existingId, factoryObject)).thenReturn(factoryObject);
        doThrow(ResourceNotFoundException.class).when(crud).update(nonExistingId, nonExistingObject);
        doThrow(DuplicatedResourceException.class).when(crud).update(existingId, duplicatedObject);

        // delete
        doNothing().when(crud).delete(existingId);
        doThrow(ResourceNotFoundException.class).when(crud).delete(nonExistingId);

        // inactivate
        doNothing().when(crud).inactivate(existingId);
        doThrow(ResourceNotFoundException.class).when(crud).inactivate(nonExistingId);
    }

    public void findAllActiveAndCurrentOneShouldReturnList() throws Exception {
        ApiTestUtil.performRequest(mockMvc, HttpMethod.GET, baseUrl + "/activeAndCurrentOne?id=" + existingId, "{}", adminAccessToken, status().isOk());
        ApiTestUtil.performRequest(mockMvc, HttpMethod.GET, baseUrl + "/activeAndCurrentOne?id=" + nonExistingId, "{}", adminAccessToken, status().isOk());
        ApiTestUtil.performRequest(mockMvc, HttpMethod.GET, baseUrl + "/activeAndCurrentOne?id=", "{}", adminAccessToken, status().isOk());
        ApiTestUtil.performRequest(mockMvc, HttpMethod.GET, baseUrl + "/activeAndCurrentOne?id=" + existingId, "{}", readOnlyAccessToken, status().isOk());
        ApiTestUtil.performRequest(mockMvc, HttpMethod.GET, baseUrl + "/activeAndCurrentOne?id=" + nonExistingId, "{}", readOnlyAccessToken, status().isOk());
        ApiTestUtil.performRequest(mockMvc, HttpMethod.GET, baseUrl + "/activeAndCurrentOne?id=", "{}", readOnlyAccessToken, status().isOk());
    }

    public void findShouldReturnObject() throws Exception {
        ApiTestUtil.performRequest(mockMvc, HttpMethod.GET, baseUrl + "/" + existingId, "{}", adminAccessToken, status().isOk());
        ApiTestUtil.performRequest(mockMvc, HttpMethod.GET, baseUrl + "/" + existingId, "{}", readOnlyAccessToken, status().isOk());
    }

    public void findShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() throws Exception {
        ApiTestUtil.performRequest(mockMvc, HttpMethod.GET, baseUrl + "/" + nonExistingId, "{}", adminAccessToken, status().isNotFound());
        ApiTestUtil.performRequest(mockMvc, HttpMethod.GET, baseUrl + "/" + nonExistingId, "{}", readOnlyAccessToken, status().isNotFound());
    }

    public void insertShouldReturnObject() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(factoryObject);
        ApiTestUtil.performRequest(mockMvc, HttpMethod.POST, baseUrl, jsonBody, adminAccessToken, status().isCreated());
    }

    public void insertShouldThrowDuplicatedResourceExceptionWhenObjectHasDuplicatedDescription() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(duplicatedObject);
        ApiTestUtil.performRequest(mockMvc, HttpMethod.POST, baseUrl, jsonBody, adminAccessToken, status().isConflict());
    }

    public void updateShouldReturnOkWhenValidData() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(factoryObject);
        ApiTestUtil.performRequest(mockMvc, HttpMethod.PUT, baseUrl + "/" + existingId, jsonBody, adminAccessToken, status().isOk());
    }

    public void updateShouldThrowResourceNotFoundExceptionWhenObjectIdDoesNotExists() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(nonExistingObject);
        ApiTestUtil.performRequest(mockMvc, HttpMethod.PUT, baseUrl + "/" + nonExistingId, jsonBody, adminAccessToken, status().isNotFound());
    }

    public void updateShouldThrowDuplicatedResourceExceptionWhenObjectHasDuplicatedDescription() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(duplicatedObject);
        ApiTestUtil.performRequest(mockMvc, HttpMethod.PUT, baseUrl + "/" + existingId, jsonBody, adminAccessToken,status().isConflict());
    }

    public void inactivateShouldReturnOkWhenIdExists() throws Exception {
        ApiTestUtil.performRequest(mockMvc, HttpMethod.PUT, baseUrl + "/inactivate/" + existingId, "{}", adminAccessToken, status().isOk());
    }

    public void inactivateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() throws Exception {
        ApiTestUtil.performRequest(mockMvc, HttpMethod.PUT, baseUrl + "/inactivate/" + nonExistingId, "{}", adminAccessToken, status().isNotFound());
    }

    public void deleteShouldReturnNoContentWhenIdExists() throws Exception {
        ApiTestUtil.performRequest(mockMvc, HttpMethod.DELETE, baseUrl + "/" + existingId, "{}", adminAccessToken, status().isNoContent());
    }

    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() throws Exception {
        ApiTestUtil.performRequest(mockMvc, HttpMethod.DELETE, baseUrl + "/" + nonExistingId, "{}", adminAccessToken, status().isNotFound());
    }

    protected void validate(D object) throws Exception {
        String jsonBody = objectMapper.writeValueAsString(object);
        ApiTestUtil.performRequest(mockMvc, HttpMethod.PUT, baseUrl + "/" + existingId, jsonBody, adminAccessToken, status().isUnprocessableEntity());
    }
}
