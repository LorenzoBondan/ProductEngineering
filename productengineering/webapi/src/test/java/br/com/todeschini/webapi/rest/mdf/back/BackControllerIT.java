package br.com.todeschini.webapi.rest.mdf.back;

import br.com.todeschini.domain.business.mdf.back.DBack;
import br.com.todeschini.persistence.mdf.back.BackRepository;
import br.com.todeschini.persistence.mdf.back.CrudBackImpl;
import br.com.todeschini.webapi.ApiTestUtil;
import br.com.todeschini.webapi.BaseControllerIT;
import javax.transaction.Transactional;

import br.com.todeschini.webapi.rest.packaging.plastic.PlasticFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class BackControllerIT extends BaseControllerIT<DBack> {

    @MockBean
    private CrudBackImpl crud;
    @MockBean
    private BackRepository repository;

    @BeforeEach
    void setUp() throws Exception {
        setConfiguration("/backs");

        factoryObject = BackFactory.createDBack();
        duplicatedObject = BackFactory.createDuplicatedDBack();
        nonExistingObject = BackFactory.createNonExistingDBack(nonExistingId);

        setCrudBehavior(factoryObject, nonExistingObject, crud);

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
        findShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists();
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
    public void inactivateShouldReturnOkWhenIdExistsTest() throws Exception {
        inactivateShouldReturnOkWhenIdExists();
    }

    @Test
    public void inactivateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExistsTest() throws Exception {
        inactivateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists();
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
        String blank = "", smallerSize = "a", biggerSize = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

        factoryObject = BackFactory.createDBack();
        factoryObject.setCode(null);
        validate(factoryObject);

        factoryObject = BackFactory.createDBack();
        factoryObject.setCode(-1L);
        validate(factoryObject);

        factoryObject = BackFactory.createDBack();
        factoryObject.setCode(1L);
        validate(factoryObject);

        factoryObject = BackFactory.createDBack();
        factoryObject.setCode(1111111111111111111L);
        validate(factoryObject);

        factoryObject = BackFactory.createDBack();
        factoryObject.setDescription(null);
        validate(factoryObject);

        factoryObject = BackFactory.createDBack();
        factoryObject.setDescription(blank);
        validate(factoryObject);

        factoryObject = BackFactory.createDBack();
        factoryObject.setDescription(smallerSize);
        validate(factoryObject);

        factoryObject = BackFactory.createDBack();
        factoryObject.setDescription(biggerSize);
        validate(factoryObject);

        factoryObject = BackFactory.createDBack();
        factoryObject.setFamily("");
        validate(factoryObject);

        factoryObject = BackFactory.createDBack();
        factoryObject.setFamily(smallerSize);
        validate(factoryObject);

        factoryObject = BackFactory.createDBack();
        factoryObject.setFamily(biggerSize);
        validate(factoryObject);

        factoryObject = BackFactory.createDBack();
        factoryObject.setImplementation(LocalDate.of(1000,1,1));
        validate(factoryObject);

        factoryObject = BackFactory.createDBack();
        factoryObject.setLostPercentage(-1.0);
        validate(factoryObject);

        factoryObject = BackFactory.createDBack();
        factoryObject.setSuffix(null);
        validate(factoryObject);

        factoryObject = BackFactory.createDBack();
        factoryObject.setSuffix(-1);
        validate(factoryObject);

        factoryObject = BackFactory.createDBack();
        factoryObject.setThickness(null);
        validate(factoryObject);

        factoryObject = BackFactory.createDBack();
        factoryObject.setThickness(-1.0);
        validate(factoryObject);

        factoryObject = BackFactory.createDBack();
        factoryObject.setMeasure1(null);
        validate(factoryObject);

        factoryObject = BackFactory.createDBack();
        factoryObject.setMeasure1(-1);
        validate(factoryObject);

        factoryObject = BackFactory.createDBack();
        factoryObject.setMeasure2(null);
        validate(factoryObject);

        factoryObject = BackFactory.createDBack();
        factoryObject.setMeasure2(-1);
        validate(factoryObject);
    }
}
