package br.com.todeschini.webapi.rest.auth;

import br.com.todeschini.webapi.UserTest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthorizationTests {

    @Value("${todeschini.users.readOnly.email}")
    private String readOnlyEmail;
    @Value("${todeschini.users.readOnly.password}")
    private String readOnlyPassword;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TokenUtil tokenUtil;

    @Test
    public void testCrudAuthorization() throws Exception {
        String readOnlyAccessToken = tokenUtil.obtainAccessToken(mockMvc, new UserTest(readOnlyEmail, readOnlyPassword));
        String invalidAccessToken = "invalidAccessToken";

        List<String> entities = Arrays.asList(
                // aluminium
                "aluminiumSons",
                "aluminiumTypes",
                "drawerPulls",
                "glasses",
                "moldings",
                "screws",
                "trySquares",
                "usedDrawerPulls",
                "usedGlasses",
                "usedMoldings",
                "usedScrews",
                "usedTrySquares",

                // guides
                "guides",
                "guideMachines",
                "machineGroups",
                "machines",

                // mdf
                "backs",
                "paintingTypes",
                "paintings",
                "paintingBorderBackgrounds",
                "polyesters",
                "paintingSons",
                "usedBackSheets",
                "usedPaintings",
                "usedPaintingBorderBackgrounds",
                "usedPolyesters",

                // mdp
                "edgeBandings",
                "glues",
                "sheets",
                "usedSheets",
                "usedGlues",
                "usedEdgeBandings",

                // packaging
                "cornerBrackets",
                "ghosts",
                "nonwovenFabrics",
                "plastics",
                "polyethylenes",
                "usedCornerBrackets",
                "usedNonwovenFabrics",
                "usedPlastics",
                "usedPolyethylenes",

                // public
                "attachments",
                "colors",
                "fathers",
                "items",
                "materials",
                "sons"
                );

        // Endpoints e métodos para operações de leitura
        List<EndpointMethod> readEndpoints = new java.util.ArrayList<>(entities.stream()
                .flatMap(entity -> Stream.of(
                        new EndpointMethod("/" + entity + "?name=", HttpMethod.GET),
                        new EndpointMethod("/" + entity + "/activeAndCurrentOne/1", HttpMethod.GET),
                        new EndpointMethod("/" + entity + "/1", HttpMethod.GET)
                ))
                .collect(Collectors.toList()));

        // Endpoints e métodos para operações de escrita
        List<EndpointMethod> writeEndpoints = new java.util.ArrayList<>(entities.stream()
                .flatMap(entity -> Stream.of(
                        new EndpointMethod("/" + entity, HttpMethod.POST),
                        new EndpointMethod("/" + entity, HttpMethod.PUT),
                        new EndpointMethod("/" + entity + "/inactivate/1", HttpMethod.PUT),
                        new EndpointMethod("/" + entity + "/1", HttpMethod.DELETE)
                ))
                .collect(Collectors.toList()));

        // Adicionar mais rotas fora do CRUD, se necessário
        // writeEndpoints.add(new EndpointMethod("/exemplo/ex", HttpMethod.POST));

        // Testar autorização para operações de leitura com token inválido
        for (EndpointMethod endpoint : readEndpoints) {
            testUnauthorizedAccessForMethod(endpoint.getEndpoint(), endpoint.getMethod(), invalidAccessToken);
        }

        // Testar autorização para operações de escrita com token inválido
        for (EndpointMethod endpoint : writeEndpoints) {
            testUnauthorizedAccessForMethod(endpoint.getEndpoint(), endpoint.getMethod(), invalidAccessToken);
        }

        /*
        // Testar autorização para operações de escrita com token de leitura
        for (EndpointMethod endpoint : writeEndpoints) {
            testForbiddenAccessForMethod(endpoint.getEndpoint(), endpoint.getMethod(), readOnlyAccessToken);
        }
        */
    }

    private void testUnauthorizedAccessForMethod(String endpoint, HttpMethod method, String accessToken) throws Exception {
        mockMvc.perform(request(method, endpoint)
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    private void testForbiddenAccessForMethod(String endpoint, HttpMethod method, String accessToken) throws Exception {
        mockMvc.perform(request(method, endpoint)
                        .header("Authorization", "Bearer " + accessToken)
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Getter
    @AllArgsConstructor
    private static class EndpointMethod {
        private final String endpoint;
        private final HttpMethod method;
    }
}
