package br.com.todeschini.webapi.rest.mdf.painting;

import br.com.todeschini.domain.business.mdf.painting.DPainting;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.persistence.mdf.painting.CrudPaintingImpl;
import br.com.todeschini.persistence.mdf.painting.PaintingRepository;
import br.com.todeschini.webapi.ApiTestUtil;
import br.com.todeschini.webapi.BaseControllerIT;
import javax.transaction.Transactional;

import br.com.todeschini.webapi.rest.mdf.back.BackFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class PaintingControllerIT extends BaseControllerIT<DPainting> {

    @MockBean
    private CrudPaintingImpl crud;
    @MockBean
    private PaintingRepository repository;

    @BeforeEach
    void setUp() throws Exception {
        setConfiguration("/paintings");

        Long duplicatedId = 2L;

        factoryObject = PaintingFactory.createDPainting();
        duplicatedObject = PaintingFactory.createDuplicatedDPainting(duplicatedId);
        nonExistingObject = PaintingFactory.createNonExistingDPainting(nonExistingId);

        setCrudBehavior(factoryObject, nonExistingObject, crud);

        // findAll
        when(repository.findByStatusInAndDescriptionContainingIgnoreCase(anyList(), anyString(), any(), any()))
                .thenReturn(Page.empty());

        // custom duplicated
        doThrow(DuplicatedResourceException.class).when(crud).findByColorAndPaintingType(duplicatedId, duplicatedId);
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

        factoryObject = PaintingFactory.createDPainting();
        factoryObject.setCode(null);
        validate(factoryObject);

        factoryObject = PaintingFactory.createDPainting();
        factoryObject.setCode(-1L);
        validate(factoryObject);

        factoryObject = PaintingFactory.createDPainting();
        factoryObject.setCode(1L);
        validate(factoryObject);

        factoryObject = PaintingFactory.createDPainting();
        factoryObject.setCode(1111111111111111111L);
        validate(factoryObject);

        factoryObject = PaintingFactory.createDPainting();
        factoryObject.setDescription(null);
        validate(factoryObject);

        factoryObject = PaintingFactory.createDPainting();
        factoryObject.setDescription(blank);
        validate(factoryObject);

        factoryObject = PaintingFactory.createDPainting();
        factoryObject.setDescription(smallerSize);
        validate(factoryObject);

        factoryObject = PaintingFactory.createDPainting();
        factoryObject.setDescription(biggerSize);
        validate(factoryObject);

        factoryObject = PaintingFactory.createDPainting();
        factoryObject.setFamily("");
        validate(factoryObject);

        factoryObject = PaintingFactory.createDPainting();
        factoryObject.setFamily(smallerSize);
        validate(factoryObject);

        factoryObject = PaintingFactory.createDPainting();
        factoryObject.setFamily(biggerSize);
        validate(factoryObject);

        factoryObject = PaintingFactory.createDPainting();
        factoryObject.setImplementation(LocalDate.of(1000,1,1));
        validate(factoryObject);

        factoryObject = PaintingFactory.createDPainting();
        factoryObject.setLostPercentage(-1.0);
        validate(factoryObject);

        factoryObject = PaintingFactory.createDPainting();
        factoryObject.setPaintingType(null);
        validate(factoryObject);
    }
}
