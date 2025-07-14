package br.com.todeschini.libtestcommonswebapi;

import br.com.todeschini.libexceptionhandler.controllers.ControllerExceptionHandler;
import br.com.todeschini.libvalidationhandler.contracts.BaseService;
import br.com.todeschini.libvalidationhandler.pageable.PageableRequest;
import br.com.todeschini.libvalidationhandler.pageable.Paged;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureMockMvc
public abstract class BaseControllerMVCTest<T, S extends BaseService<T>> {

    protected MockMvc mockMvc;
    protected ObjectMapper objectMapper;
    protected S service;
    protected T domain;

    protected abstract String getRoute();
    protected abstract Integer getId();
    protected abstract T createDomainObject(Integer id);
    protected abstract Class<S> getServiceClass();

    @BeforeEach
    void setUp() {
        service = mock(getServiceClass());
        mockMvc = MockMvcBuilders.standaloneSetup(getController())
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        domain = createDomainObject(getId());
    }

    protected abstract Object getController();

    protected void performFindAll(List<T> list) throws Exception {
        Paged<T> pagedItems = new Paged<>();
        pagedItems.setContent(list);
        when(service.findAll(any(PageableRequest.class))).thenReturn(pagedItems);

        mockMvc.perform(get(getRoute())
                        .param("columns", "")
                        .param("operations", "")
                        .param("values", "")
                        .param("page", "0")
                        .param("pageSize", "10")
                        .param("sort", "id;d"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(list.size()));
    }

    protected void performFindById(Integer id) throws Exception {
        when(service.find(id)).thenReturn(domain);

        mockMvc.perform(get(getRoute() + "/" + id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id));

        verify(service).find(id);
    }

    protected void performCreate(T domain) throws Exception {
        when(service.create(any())).thenReturn(domain);

        String jsonRequest = objectMapper.writeValueAsString(domain);

        mockMvc.perform(post(getRoute())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(getId()));
    }

    protected void performUpdate(T domain, String attributeName, String attributeValue) throws Exception {
        when(service.update(any())).thenReturn(domain);

        String jsonRequest = objectMapper.writeValueAsString(domain);

        mockMvc.perform(put(getRoute())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$." + attributeName).value(attributeValue));
    }

    protected void performDelete(List<Integer> ids) throws Exception {
        doNothing().when(service).delete(anyInt());

        for (Integer id : ids) {
            mockMvc.perform(delete(getRoute())
                            .param("id", id.toString()))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        verify(service, times(ids.size())).delete(anyInt());
    }
}
