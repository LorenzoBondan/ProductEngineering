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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthorizationTests {

    @Value("${todeschini.users.operator.email}")
    private String operatorEmail;
    @Value("${todeschini.users.operator.password}")
    private String operatorPassword;
    @Value("${todeschini.users.analyst.email}")
    private String analystEmail;
    @Value("${todeschini.users.analyst.password}")
    private String analystPassword;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TokenUtil tokenUtil;

    @Test
    public void testCrudAuthorization() throws Exception {
        String operatorAccessToken = tokenUtil.obtainAccessToken(mockMvc, new UserTest(operatorEmail, operatorPassword));
        String analystAccessToken = tokenUtil.obtainAccessToken(mockMvc, new UserTest(analystEmail, analystPassword));
        String invalidAccessToken = "invalidAccessToken";

        List<String> entities = Arrays.asList(
                // audit
                "audit",

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
                "mdpSons",
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

        List<String> analystEntities = Arrays.asList(
                // aluminium
                "aluminiumTypes",
                "drawerPulls",
                "glasses",
                "moldings",
                "screws",
                "trySquares",

                // guides
                "machineGroups",
                "machines",

                // mdf
                "paintingTypes",
                "paintings",
                "paintingBorderBackgrounds",
                "polyesters",

                // mdp
                "edgeBandings",
                "glues",
                "sheets",

                // packaging
                "cornerBrackets",
                "nonwovenFabrics",
                "plastics",
                "polyethylenes",

                // public
                "colors",
                "materials"
        );

        // Endpoints e métodos para operações de leitura
        List<EndpointMethod> readEndpoints = entities.stream()
                .flatMap(entity -> Stream.of(
                        new EndpointMethod("/" + entity + "?name=", HttpMethod.GET),
                        new EndpointMethod("/" + entity + "/activeAndCurrentOne?id=", HttpMethod.GET),
                        new EndpointMethod("/" + entity + "/" + anyLong(), HttpMethod.GET)
                )).collect(Collectors.toList());

        // Endpoints e métodos para operações de escrita
        List<EndpointMethod> writeEndPoints = analystEntities.stream()
                .flatMap(entity -> Stream.of(
                        new EndpointMethod("/" + entity, HttpMethod.POST),
                        new EndpointMethod("/" + entity + "/1", HttpMethod.PUT),
                        new EndpointMethod("/" + entity + "/inactivate/1", HttpMethod.PUT),
                        new EndpointMethod("/" + entity + "/1", HttpMethod.DELETE)
                ))
                .collect(Collectors.toList());

        writeEndPoints.add(new EndpointMethod("/trash?username=" + anyString() + "&startDate=" + anyString() + "&endDate=" + anyString() + "&table=" + anyString(), HttpMethod.GET));
        writeEndPoints.add(new EndpointMethod("/trash/retrieve/" + anyLong() + "?retrieveDependencies=" + anyBoolean(), HttpMethod.GET));

        List<EndpointMethod> adminEndPoints = new ArrayList<>();

        adminEndPoints.add(new EndpointMethod("/roles?authority=", HttpMethod.GET));
        adminEndPoints.add(new EndpointMethod("/roles", HttpMethod.POST));
        adminEndPoints.add(new EndpointMethod("/roles/" + anyLong(), HttpMethod.PUT));
        adminEndPoints.add(new EndpointMethod("/roles/" + anyLong(), HttpMethod.DELETE));

        adminEndPoints.add(new EndpointMethod("/users", HttpMethod.POST));
        adminEndPoints.add(new EndpointMethod("/users/" + anyLong(), HttpMethod.DELETE));

        adminEndPoints.add(new EndpointMethod("/trash?username=" + anyString() + "&startDate=" + anyString() + "&endDate=" + anyString() + "&table=" + anyString(), HttpMethod.GET));
        adminEndPoints.add(new EndpointMethod("/trash/retrieve/" + anyLong() + "?retrieveDependencies=" + anyBoolean(), HttpMethod.GET));

        adminEndPoints.add(new EndpointMethod("/audit?tabname=" + anyString() + "&operation=" + anyString() + "&idName=" + anyString() + "&idValue=" + anyString() + "&startDate=" + anyString() + "&endDate=" + anyString(), HttpMethod.GET));

        // Testar autorização para operações de operador com token inválido
        for (EndpointMethod endpoint : readEndpoints) {
            testUnauthorizedAccessForMethod(endpoint.getEndpoint(), endpoint.getMethod(), invalidAccessToken);
        }

        // Testar autorização para operações de analista com token inválido
        for (EndpointMethod endpoint : writeEndPoints) {
            testUnauthorizedAccessForMethod(endpoint.getEndpoint(), endpoint.getMethod(), invalidAccessToken);
        }

        // Testar autorização para operações de admin com token inválido
        for (EndpointMethod endpoint : adminEndPoints) {
            testUnauthorizedAccessForMethod(endpoint.getEndpoint(), endpoint.getMethod(), invalidAccessToken);
        }

        // Testar autorização para operações de analista com token de operador
        for (EndpointMethod endpoint : writeEndPoints) {
            testForbiddenAccessForMethod(endpoint.getEndpoint(), endpoint.getMethod(), operatorAccessToken);
        }

        // Testar autorização para operações de admin com token de operador
        for (EndpointMethod endpoint : adminEndPoints) {
            testForbiddenAccessForMethod(endpoint.getEndpoint(), endpoint.getMethod(), operatorAccessToken);
        }

        // Testar autorização para operações de admin com token de analista (profile:dev)
        for (EndpointMethod endpoint : adminEndPoints) {
            testForbiddenAccessForMethod(endpoint.getEndpoint(), endpoint.getMethod(), analystAccessToken);
        }
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
