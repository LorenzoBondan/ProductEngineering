package br.com.todeschini.webapi.rest.aluminium.usedglass;

import br.com.todeschini.domain.business.aluminium.usedglass.DUsedGlass;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.persistence.aluminium.usedglass.CrudUsedGlassImpl;
import br.com.todeschini.persistence.aluminium.usedglass.UsedGlassRepository;
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
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class UsedGlassControllerIT extends BaseControllerIT<DUsedGlass> {

    @MockBean
    private CrudUsedGlassImpl crud;
    @MockBean
    private UsedGlassRepository repository;

    @BeforeEach
    void setUp() throws Exception {
        setConfiguration("/usedGlasses");

        Long duplicatedId = 2L;

        factoryObject = UsedGlassFactory.createDUsedGlass();
        duplicatedObject = UsedGlassFactory.createDuplicatedDUsedGlass(duplicatedId);
        nonExistingObject = UsedGlassFactory.createNonExistingDUsedGlass(nonExistingId);

        setCrudBehavior(factoryObject, nonExistingObject, crud);

        // findAll
        when(repository.findByStatusIn(anyList(), any(), any()))
                .thenReturn(Page.empty());

        // custom duplicated
        doThrow(DuplicatedResourceException.class).when(crud).findByGlassAndAluminiumSon(duplicatedId, duplicatedId);
    }

    @Test
    public void pesquisarTodosPorDescricaoEListaDeSituacoesShouldReturnPage() throws Exception {
        ApiTestUtil.performRequest(mockMvc, HttpMethod.GET, baseUrl, statusListBody, adminAccessToken, status().isOk());
        ApiTestUtil.performRequest(mockMvc, HttpMethod.GET, baseUrl, statusListBody, readOnlyAccessToken, status().isOk());
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
        factoryObject = UsedGlassFactory.createDUsedGlass();
        factoryObject.setAluminiumSonCode(null);
        validate(factoryObject);

        factoryObject = UsedGlassFactory.createDUsedGlass();
        factoryObject.setGlassCode(null);
        validate(factoryObject);

        factoryObject = UsedGlassFactory.createDUsedGlass();
        factoryObject.setGlassCode(ValidationConstants.longNegative);
        validate(factoryObject);

        factoryObject = UsedGlassFactory.createDUsedGlass();
        factoryObject.setQuantity(null);
        validate(factoryObject);

        factoryObject = UsedGlassFactory.createDUsedGlass();
        factoryObject.setQuantity(ValidationConstants.doubleNegative);
        validate(factoryObject);
    }
}
