package br.com.todeschini.webapi.rest.mdf.usedbacksheet;

import br.com.todeschini.domain.business.mdf.usedbacksheet.DUsedBackSheet;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.persistence.mdf.usedbacksheet.CrudUsedBackSheetImpl;
import br.com.todeschini.persistence.mdf.usedbacksheet.UsedBackSheetRepository;
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
public class UsedBackSheetControllerIT extends BaseControllerIT<DUsedBackSheet> {

    @MockBean
    private CrudUsedBackSheetImpl crud;
    @MockBean
    private UsedBackSheetRepository repository;

    @BeforeEach
    void setUp() throws Exception {
        setConfiguration("/usedBackSheets");

        Long duplicatedId = 2L;

        factoryObject = UsedBackSheetFactory.createDUsedBackSheet();
        duplicatedObject = UsedBackSheetFactory.createDuplicatedDUsedBackSheet(duplicatedId);
        nonExistingObject = UsedBackSheetFactory.createNonExistingDUsedBackSheet(nonExistingId);

        setCrudBehavior(factoryObject, nonExistingObject, crud);

        // findAll
        when(repository.findByStatusIn(anyList(), any(), any()))
                .thenReturn(Page.empty());

        // custom duplicated
        doThrow(DuplicatedResourceException.class).when(crud).findByBackAndSheet(duplicatedId, duplicatedId);
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
        factoryObject = UsedBackSheetFactory.createDUsedBackSheet();
        factoryObject.setSheetCode(null);
        validate(factoryObject);

        factoryObject = UsedBackSheetFactory.createDUsedBackSheet();
        factoryObject.setSheetCode(ValidationConstants.longNegative);
        validate(factoryObject);

        factoryObject = UsedBackSheetFactory.createDUsedBackSheet();
        factoryObject.setBackCode(null);
        validate(factoryObject);

        factoryObject = UsedBackSheetFactory.createDUsedBackSheet();
        factoryObject.setBackCode(ValidationConstants.longNegative);
        validate(factoryObject);

        factoryObject = UsedBackSheetFactory.createDUsedBackSheet();
        factoryObject.setNetQuantity(ValidationConstants.doubleNegative);
        validate(factoryObject);

        factoryObject = UsedBackSheetFactory.createDUsedBackSheet();
        factoryObject.setGrossQuantity(ValidationConstants.doubleNegative);
        validate(factoryObject);
    }
}
