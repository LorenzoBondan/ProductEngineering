package br.com.todeschini.webapi.rest.packaging.usednonwovenfabric;

import br.com.todeschini.domain.business.packaging.usednonwovenfabric.DUsedNonwovenFabric;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.persistence.packaging.usednonwovenfabric.CrudUsedNonwovenFabricImpl;
import br.com.todeschini.persistence.packaging.usednonwovenfabric.UsedNonwovenFabricRepository;
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
public class UsedNonwovenFabricControllerIT extends BaseControllerIT<DUsedNonwovenFabric> {

    @MockBean
    private CrudUsedNonwovenFabricImpl crud;
    @MockBean
    private UsedNonwovenFabricRepository repository;

    @BeforeEach
    void setUp() throws Exception {
        setConfiguration("/usedNonwovenFabrics");

        Long duplicatedId = 2L;
        String duplicatedGhostCode = "221F99";

        factoryObject = UsedNonwovenFabricFactory.createDUsedNonwovenFabric();
        duplicatedObject = UsedNonwovenFabricFactory.createDuplicatedDUsedNonwovenFabric(duplicatedId, duplicatedGhostCode);
        nonExistingObject = UsedNonwovenFabricFactory.createNonExistingDUsedNonwovenFabric(nonExistingId);

        setCrudBehavior(factoryObject, nonExistingObject, crud);

        // findAll
        when(repository.findByStatusIn(anyList(), any(), any()))
                .thenReturn(Page.empty());

        // custom duplicated
        doThrow(DuplicatedResourceException.class).when(crud).findByNonwovenFabricAndGhost(duplicatedId, duplicatedGhostCode);
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

        factoryObject = UsedNonwovenFabricFactory.createDUsedNonwovenFabric();
        factoryObject.setGhostCode(null);
        validate(factoryObject);

        factoryObject = UsedNonwovenFabricFactory.createDUsedNonwovenFabric();
        factoryObject.setNonwovenFabricCode(null);
        validate(factoryObject);

        factoryObject = UsedNonwovenFabricFactory.createDUsedNonwovenFabric();
        factoryObject.setNonwovenFabricCode(-1L);
        validate(factoryObject);

        factoryObject = UsedNonwovenFabricFactory.createDUsedNonwovenFabric();
        factoryObject.setNetQuantity(negative);
        validate(factoryObject);

        factoryObject = UsedNonwovenFabricFactory.createDUsedNonwovenFabric();
        factoryObject.setGrossQuantity(negative);
        validate(factoryObject);
    }
}
