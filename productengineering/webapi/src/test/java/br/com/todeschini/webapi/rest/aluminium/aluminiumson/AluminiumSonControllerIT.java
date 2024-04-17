package br.com.todeschini.webapi.rest.aluminium.aluminiumson;

import br.com.todeschini.domain.business.aluminium.aluminiumson.DAluminiumSon;
import br.com.todeschini.persistence.aluminium.aluminiumson.CrudAluminiumSonImpl;
import br.com.todeschini.persistence.publico.son.SonRepository;
import br.com.todeschini.webapi.ApiTestUtil;
import br.com.todeschini.webapi.BaseControllerIT;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class AluminiumSonControllerIT extends BaseControllerIT<DAluminiumSon> {

    @MockBean
    private CrudAluminiumSonImpl crud;
    @MockBean
    private SonRepository repository;

    @BeforeEach
    void setUp() throws Exception {
        setConfiguration("/aluminiumSons");

        factoryObject = AluminiumSonFactory.createDAluminiumSon();
        duplicatedObject = AluminiumSonFactory.createDuplicatedDAluminiumSon();
        nonExistingObject = AluminiumSonFactory.createNonExistingDAluminiumSon(nonExistingId);

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
        Integer negative = -1;

        factoryObject = AluminiumSonFactory.createDAluminiumSon();
        factoryObject.setDescription(null);
        validate(factoryObject);

        factoryObject = AluminiumSonFactory.createDAluminiumSon();
        factoryObject.setDescription(blank);
        validate(factoryObject);

        factoryObject = AluminiumSonFactory.createDAluminiumSon();
        factoryObject.setDescription(smallerSize);
        validate(factoryObject);

        factoryObject = AluminiumSonFactory.createDAluminiumSon();
        factoryObject.setDescription(biggerSize);
        validate(factoryObject);

        factoryObject = AluminiumSonFactory.createDAluminiumSon();
        factoryObject.setMeasure1(negative);
        validate(factoryObject);

        factoryObject = AluminiumSonFactory.createDAluminiumSon();
        factoryObject.setMeasure2(negative);
        validate(factoryObject);

        factoryObject = AluminiumSonFactory.createDAluminiumSon();
        factoryObject.setMeasure3(negative);
        validate(factoryObject);

        factoryObject = AluminiumSonFactory.createDAluminiumSon();
        factoryObject.setColor(null);
        validate(factoryObject);

        factoryObject = AluminiumSonFactory.createDAluminiumSon();
        factoryObject.setFatherCode(-1L);
        validate(factoryObject);

        factoryObject = AluminiumSonFactory.createDAluminiumSon();
        factoryObject.setAluminiumType(null);
        validate(factoryObject);
    }
}
