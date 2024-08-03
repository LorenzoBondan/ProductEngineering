package br.com.todeschini.webapi.rest.mdp.edgebanding;

import br.com.todeschini.domain.business.mdp.edgebanding.DEdgeBanding;
import br.com.todeschini.persistence.mdp.edgebanding.CrudEdgeBandingImpl;
import br.com.todeschini.persistence.mdp.edgebanding.EdgeBandingRepository;
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
public class EdgeBandingControllerIT extends BaseControllerIT<DEdgeBanding> {

    @MockBean
    private CrudEdgeBandingImpl crud;
    @MockBean
    private EdgeBandingRepository repository;

    @BeforeEach
    void setUp() throws Exception {
        setConfiguration("/edgeBandings");

        factoryObject = EdgeBandingFactory.createDEdgeBanding();
        duplicatedObject = EdgeBandingFactory.createDuplicatedDEdgeBanding();
        nonExistingObject = EdgeBandingFactory.createNonExistingDEdgeBanding(nonExistingId);

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
        factoryObject = EdgeBandingFactory.createDEdgeBanding();
        factoryObject.setCode(null);
        validate(factoryObject);

        factoryObject = EdgeBandingFactory.createDEdgeBanding();
        factoryObject.setCode(ValidationConstants.longNegative);
        validate(factoryObject);

        factoryObject = EdgeBandingFactory.createDEdgeBanding();
        factoryObject.setCode(ValidationConstants.longOneDigit);
        validate(factoryObject);

        factoryObject = EdgeBandingFactory.createDEdgeBanding();
        factoryObject.setCode(ValidationConstants.longManyDigits);
        validate(factoryObject);

        factoryObject = EdgeBandingFactory.createDEdgeBanding();
        factoryObject.setDescription(null);
        validate(factoryObject);

        factoryObject = EdgeBandingFactory.createDEdgeBanding();
        factoryObject.setDescription(ValidationConstants.blank);
        validate(factoryObject);

        factoryObject = EdgeBandingFactory.createDEdgeBanding();
        factoryObject.setDescription(ValidationConstants.smallerSize);
        validate(factoryObject);

        factoryObject = EdgeBandingFactory.createDEdgeBanding();
        factoryObject.setDescription(ValidationConstants.biggerSize);
        validate(factoryObject);

        factoryObject = EdgeBandingFactory.createDEdgeBanding();
        factoryObject.setFamily(ValidationConstants.blank);
        validate(factoryObject);

        factoryObject = EdgeBandingFactory.createDEdgeBanding();
        factoryObject.setFamily(ValidationConstants.smallerSize);
        validate(factoryObject);

        factoryObject = EdgeBandingFactory.createDEdgeBanding();
        factoryObject.setFamily(ValidationConstants.biggerSize);
        validate(factoryObject);

        factoryObject = EdgeBandingFactory.createDEdgeBanding();
        factoryObject.setImplementation(ValidationConstants.pastDate);
        validate(factoryObject);

        factoryObject = EdgeBandingFactory.createDEdgeBanding();
        factoryObject.setLostPercentage(ValidationConstants.doubleNegative);
        validate(factoryObject);

        factoryObject = EdgeBandingFactory.createDEdgeBanding();
        factoryObject.setHeight(null);
        validate(factoryObject);

        factoryObject = EdgeBandingFactory.createDEdgeBanding();
        factoryObject.setHeight(ValidationConstants.intNegative);
        validate(factoryObject);

        factoryObject = EdgeBandingFactory.createDEdgeBanding();
        factoryObject.setThickness(null);
        validate(factoryObject);

        factoryObject = EdgeBandingFactory.createDEdgeBanding();
        factoryObject.setThickness(ValidationConstants.intNegative);
        validate(factoryObject);
    }
}
