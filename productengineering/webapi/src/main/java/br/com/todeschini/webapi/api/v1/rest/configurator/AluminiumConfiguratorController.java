package br.com.todeschini.webapi.api.v1.rest.configurator;

import br.com.todeschini.domain.business.configurator.configurators.aluminiumconfigurator.DAluminiumConfigurator;
import br.com.todeschini.domain.business.configurator.configurators.aluminiumconfigurator.api.AluminiumConfiguratorService;
import br.com.todeschini.domain.business.publico.father.DFather;
import br.com.todeschini.domain.configurator.AluminiumConfigurator;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "AluminiumConfigurator")
@RestController
@RequestMapping(value = "/aluminiumConfigurator")
public class AluminiumConfiguratorController {

    private final AluminiumConfiguratorService service;

    public AluminiumConfiguratorController(AluminiumConfiguratorService service) {
        this.service = service;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request"), // invalid data, String in Integer field
            @ApiResponse(responseCode = "401", description = "Unauthorized"), // when not logged
            @ApiResponse(responseCode = "422", description = "Invalid data"), // when some attribute is not valid
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ANALYST', 'ROLE_OPERATOR')")
    @PostMapping
    public ResponseEntity<List<DFather>> generateStruct(@RequestBody AluminiumConfigurator config,
                                                        @RequestParam("aluminiumTypeId") Long aluminiumTypeId,

                                                        @RequestParam("ghostSuffix") String ghostSuffix,

                                                        @RequestParam("drawerPullCode") Long drawerPullCode,
                                                        @RequestParam("trySquareCode") Long trySquareCode,
                                                        @RequestParam("trySquareQuantity") Integer trySquareQuantity,
                                                        @RequestParam("moldingCode") Long moldingCode,

                                                        @RequestParam("cornerBracketCode") Long cornerBracketCode,
                                                        @RequestParam("plasticCode") Long plasticCode,
                                                        @RequestParam("nonwovenFabricCode") Long nonwovenFabricCode,
                                                        @RequestParam("polyethyleneCode") Long polyethyleneCode,
                                                        @RequestParam("upper") Boolean upper, // upper plastic
                                                        @RequestParam("additional") Double additional, // additional plastic
                                                        @RequestParam("width") Integer width, // plastic width
                                                        @RequestParam("quantity") Integer quantity, // corner bracket quantity
                                                        @RequestParam("oneFace") Boolean oneFace,

                                                        @RequestParam("glueCode") Long glueCode,

                                                        @RequestParam("implementation") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate implementation
                                                          ){
        List<DFather> list = service.generateStruct(new DAluminiumConfigurator(
                config, aluminiumTypeId, ghostSuffix,
                drawerPullCode, trySquareCode, trySquareQuantity, moldingCode,
                glueCode, cornerBracketCode, plasticCode, nonwovenFabricCode, polyethyleneCode,
                upper, additional, width, //plastic
                quantity, // cornerBracket
                oneFace,
                implementation
        ));
        return ResponseEntity.ok().body(list);
    }
}

