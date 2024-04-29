package br.com.todeschini.domain.publico.color.steps;

import br.com.todeschini.domain.business.publico.color.ColorServiceImpl;
import br.com.todeschini.domain.business.publico.color.DColor;
import br.com.todeschini.domain.business.publico.color.api.FindColor;
import br.com.todeschini.domain.business.publico.color.api.InsertColor;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.domain.exceptions.ResourceNotFoundException;
import br.com.todeschini.domain.publico.color.stub.ColorInMemoryDataStore;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BuscarColorSteps {

    private final FindColor buscarColor;
    private DColor dColor;
    private Long existingId, nonExistingId;
    private Exception exp;

    public BuscarColorSteps() {
        buscarColor = new ColorServiceImpl(new ColorInMemoryDataStore());
    }

    @Dado("Um codigo existente")
    public void um_codigo_existente() {
        existingId = 1L;

        assertNotNull(existingId);
    }

    @Quando("for solicitada a busca de um registro existente")
    public void for_solicitada_a_busca_de_um_registro_existente() {
        assertNull(dColor);
        dColor = buscarColor.find(existingId);
    }

    @Então("um registro e trazido")
    public void um_registro_e_trazido() {
        assertNotNull(dColor.getCode());
    }

    @Dado("Um codigo inexistente")
    public void um_codigo_inexistente() {
        nonExistingId = 100L;

        assertNotNull(nonExistingId);
    }

    @Quando("for solicitada a busca de um registro nao existente")
    public void for_solicitada_a_busca_de_um_registro_nao_existente() {
        try{
            dColor = null;
            exp = null;
            dColor = buscarColor.find(nonExistingId);
        } catch (ResourceNotFoundException e) {
            exp = e;
        }
        assertNull(dColor);
    }

}
