package br.com.todeschini.domain.publico.color.steps;

import br.com.todeschini.domain.business.publico.color.ColorServiceImpl;
import br.com.todeschini.domain.business.publico.color.DColor;
import br.com.todeschini.domain.business.publico.color.api.InsertColor;
import br.com.todeschini.domain.exceptions.DuplicatedResourceException;
import br.com.todeschini.domain.publico.color.stub.ColorInMemoryDataStore;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CriarColorSteps {

    private final InsertColor criarColor;
    private DColor dColor, novoRegistro;
    private Exception exp;

    public CriarColorSteps() {
        criarColor = new ColorServiceImpl(new ColorInMemoryDataStore());
    }

    @Dado("Um formulario corretamente preenchido")
    public void um_formulario_corretamente_preenchido() {
        dColor = new DColor();
        dColor.setCode(301L);
        dColor.setName("Tipo 1");

        assertNotNull(dColor.getCode());
        assertNotNull(dColor.getName());
    }

    @Quando("for solicitado o cadastro de um novo registro")
    public void for_solicitado_o_cadastro_de_um_novo_registro() {
        assertNotNull(dColor);
        novoRegistro = criarColor.insert(dColor);
    }

    @Então("um novo registro de cor sera criado")
    public void um_novo_registro_de_cor_sera_criado() {
        assertNotNull(novoRegistro.getCode());
    }

    @E("o nome utilizado ja consta no banco de dados")
    public void o_nome_utilizado_ja_consta_no_banco_de_dados() {
        dColor = new DColor();
        dColor.setName("Nome 1");

        assertNotNull(dColor.getName());
    }

    @Quando("ele tentar cadastrar o registro")
    public void ele_tentar_cadastrar_o_registro() {
        try {
            novoRegistro = null;
            exp = null;
            novoRegistro = criarColor.insert(dColor);
        }catch (DuplicatedResourceException e){
            exp = e;
        }
        assertNull(novoRegistro);
    }

    @Então("uma excecao e lancada")
    public void uma_excecao_e_lancada() {
        assertNotNull(exp);
    }
}
