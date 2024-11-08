package br.com.todeschini.webapi.rest.publico.pintura;

import br.com.todeschini.domain.business.publico.pintura.DPintura;
import br.com.todeschini.persistence.entities.publico.Lixeira;
import br.com.todeschini.persistence.entities.publico.Pintura;
import br.com.todeschini.persistence.publico.history.HistoryRepository;
import br.com.todeschini.persistence.publico.lixeira.LixeiraRepository;
import br.com.todeschini.persistence.publico.pintura.PinturaRepository;
import br.com.todeschini.webapi.BaseControllerTest;
import br.com.todeschini.webapi.rest.auth.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PinturaControllerTest extends BaseControllerTest<DPintura, Pintura> {

    @Autowired
    private PinturaRepository pinturaRepository;
    @Autowired
    private LixeiraRepository lixeiraRepository;

    @Autowired
    public PinturaControllerTest(MockMvc mockMvc, ObjectMapper objectMapper, HistoryRepository historyRepository, TokenUtil tokenUtil) {
        super(mockMvc, objectMapper, historyRepository, tokenUtil);
        this.baseUrl = "/api/pintura";
    }

    @BeforeAll
    public void setUp() {
        novaEntidade = PinturaFactory.createDPintura();
        novaEntidade.setCodigo(null);
        super.setHasDescricao(true);
    }

    @Override
    protected Class<Pintura> getEntityClass() {
        return Pintura.class;
    }

    @Override
    protected String getDescricao(DPintura entidade) {
        return entidade.getDescricao();
    }

    @Override
    protected void setCodigo(DPintura entidade, Integer codigo) {
        entidade.setCodigo(codigo);
    }

    @Override
    protected void setDescricao(DPintura entidade, String descricao) {
        entidade.setDescricao(descricao);
    }

    @Test
    @Order(1)
    void deveCriarNovaEntidadeTest() throws Exception {
        super.deveCriarNovaEntidade();
    }

    @Test
    @Order(2)
    void devePesquisarComFiltrosTest() throws Exception {
        super.devePesquisarComFiltros();
    }

    @Test
    @Order(3)
    void devePesquisarPorIdTest() throws Exception {
        super.devePesquisarPorId();
    }

    @Test
    @Order(4)
    void devePesquisarTodosAtivosMaisAtualTest() throws Exception {
        super.devePesquisarTodosAtivosMaisAtual();
    }

    @Test
    @Order(5)
    void devePesquisarHistoricoTest() throws Exception {
        super.devePesquisarHistorico();
    }

    @Test
    @Order(6)
    void deveAtualizarEntidadeExistenteTest() throws Exception {
        super.deveAtualizarEntidadeExistente();
    }

    @Test
    @Order(7)
    void deveSubstituirVersaoTest() throws Exception {
        super.deveSubstituirVersao("tb_material", "cdmaterial");
    }

    @Test
    @Order(8)
    void deveInativarEntidadeTest() throws Exception {
        super.deveInativarEntidade();
    }

    @Test
    @Order(8)
    void deveDeletarEntidadeTest() throws Exception {
        super.deveDeletarEntidade();
    }

    @AfterAll
    void tearDown() {
        if (idCriado != null) {
            pinturaRepository.deleteById(idCriado);
            Map<String, Object> id = new HashMap<>();
            id.put("cdpintura", idCriado);
            Lixeira lixeira = lixeiraRepository.findByEntidadeid(id);
            lixeiraRepository.deleteById(lixeira.getId());
        }
    }
}
