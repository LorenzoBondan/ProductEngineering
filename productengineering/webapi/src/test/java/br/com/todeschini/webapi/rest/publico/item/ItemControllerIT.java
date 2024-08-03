package br.com.todeschini.webapi.rest.publico.item;

import br.com.todeschini.domain.business.publico.item.DItem;
import br.com.todeschini.persistence.publico.item.CrudItemImpl;
import br.com.todeschini.persistence.publico.item.ItemRepository;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerIT extends BaseControllerIT<DItem> {

    @MockBean
    private CrudItemImpl crud;
    @MockBean
    private ItemRepository repository;

    @BeforeEach
    void setUp() throws Exception {
        setConfiguration("/items");

        factoryObject = ItemFactory.createDItem();
        duplicatedObject = ItemFactory.createDuplicatedDItem();
        nonExistingObject = ItemFactory.createNonExistingDItem(nonExistingId);

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
        factoryObject = ItemFactory.createDItem();
        factoryObject.setCode(null);
        validate(factoryObject);

        factoryObject = ItemFactory.createDItem();
        factoryObject.setCode(ValidationConstants.longNegative);
        validate(factoryObject);

        factoryObject = ItemFactory.createDItem();
        factoryObject.setCode(ValidationConstants.longManyDigits);
        validate(factoryObject);

        factoryObject = ItemFactory.createDItem();
        factoryObject.setDescription(null);
        validate(factoryObject);

        factoryObject = ItemFactory.createDItem();
        factoryObject.setDescription(ValidationConstants.blank);
        validate(factoryObject);

        factoryObject = ItemFactory.createDItem();
        factoryObject.setDescription(ValidationConstants.smallerSize);
        validate(factoryObject);

        factoryObject = ItemFactory.createDItem();
        factoryObject.setDescription(ValidationConstants.biggerSize);
        validate(factoryObject);

        factoryObject = ItemFactory.createDItem();
        factoryObject.setMeasure1(ValidationConstants.intNegative);
        validate(factoryObject);

        factoryObject = ItemFactory.createDItem();
        factoryObject.setMeasure2(ValidationConstants.intNegative);
        validate(factoryObject);

        factoryObject = ItemFactory.createDItem();
        factoryObject.setMeasure3(ValidationConstants.intNegative);
        validate(factoryObject);
    }
}
