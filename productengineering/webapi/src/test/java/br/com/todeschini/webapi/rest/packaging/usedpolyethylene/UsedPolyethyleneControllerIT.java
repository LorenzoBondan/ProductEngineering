package br.com.todeschini.webapi.rest.packaging.usedpolyethylene;

import br.com.todeschini.domain.business.packaging.usedpolyethylene.DUsedPolyethylene;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.persistence.packaging.usedpolyethylene.CrudUsedPolyethyleneImpl;
import br.com.todeschini.persistence.packaging.usedpolyethylene.UsedPolyethyleneRepository;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class UsedPolyethyleneControllerIT extends BaseControllerIT<DUsedPolyethylene> {

    @MockBean
    private CrudUsedPolyethyleneImpl crud;
    @MockBean
    private UsedPolyethyleneRepository repository;

    @BeforeEach
    void setUp() throws Exception {
        setConfiguration("/usedPolyethylenes");

        Long duplicatedId = 2L;
        String duplicatedGhostCode = "221F99";

        factoryObject = UsedPolyethyleneFactory.createDUsedPolyethylene();
        duplicatedObject = UsedPolyethyleneFactory.createDuplicatedDUsedPolyethylene(duplicatedId, duplicatedGhostCode);
        nonExistingObject = UsedPolyethyleneFactory.createNonExistingDUsedPolyethylene(nonExistingId);

        setCrudBehavior(factoryObject, nonExistingObject, crud);

        // findAll
        when(repository.findByStatusIn(anyList(), any(), any()))
                .thenReturn(Page.empty());

        // custom duplicated
        doThrow(DuplicatedResourceException.class).when(crud).findByPolyethyleneAndGhost(duplicatedId, duplicatedGhostCode);
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
        Double negative = -1.0;

        factoryObject = UsedPolyethyleneFactory.createDUsedPolyethylene();
        factoryObject.setGhostCode(null);
        validate(factoryObject);

        factoryObject = UsedPolyethyleneFactory.createDUsedPolyethylene();
        factoryObject.setPolyethyleneCode(null);
        validate(factoryObject);

        factoryObject = UsedPolyethyleneFactory.createDUsedPolyethylene();
        factoryObject.setPolyethyleneCode(-1L);
        validate(factoryObject);

        factoryObject = UsedPolyethyleneFactory.createDUsedPolyethylene();
        factoryObject.setNetQuantity(negative);
        validate(factoryObject);

        factoryObject = UsedPolyethyleneFactory.createDUsedPolyethylene();
        factoryObject.setGrossQuantity(negative);
        validate(factoryObject);
    }
}
