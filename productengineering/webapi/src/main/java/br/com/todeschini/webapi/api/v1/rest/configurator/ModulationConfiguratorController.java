package br.com.todeschini.webapi.api.v1.rest.configurator;

import br.com.todeschini.domain.business.configurator.configurators.modulationconfigurator.DModulationConfigurator;
import br.com.todeschini.domain.business.configurator.configurators.modulationconfigurator.api.ModulationConfiguratorService;
import br.com.todeschini.domain.business.publico.father.DFather;
import br.com.todeschini.domain.configurator.ModulationConfigurator;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "ModulationConfigurator")
@RestController
@RequestMapping(value = "/modulationConfigurator")
public class ModulationConfiguratorController {

    private final ModulationConfiguratorService modulationConfiguratorService;

    public ModulationConfiguratorController(ModulationConfiguratorService modulationConfiguratorService) {
        this.modulationConfiguratorService = modulationConfiguratorService;
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
    public ResponseEntity<List<DFather>> generateModulationStruct(@RequestBody ModulationConfigurator config,

                                                                  @RequestParam("ghostSuffix") String ghostSuffix,
                                                                  @RequestParam("glueCode") Long glueCode,

                                                                  @RequestParam("cornerBracketCode") Long cornerBracketCode,
                                                                  @RequestParam("plasticCode") Long plasticCode,
                                                                  @RequestParam("nonwovenFabricCode") Long nonwovenFabricCode,
                                                                  @RequestParam("polyethyleneCode") Long polyethyleneCode,
                                                                  @RequestParam("upper") Boolean upper, // upper plastic
                                                                  @RequestParam("additional") Double additional, // additional plastic
                                                                  @RequestParam("width") Integer width, // plastic width
                                                                  @RequestParam("quantity") Integer quantity, // corner bracket quantity
                                                                  @RequestParam("oneFace") Boolean oneFace,

                                                                  @RequestParam("implementation") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate implementation
    ){
        List<DFather> list = modulationConfiguratorService.generateModulationStruct(new DModulationConfigurator(
                config,
                ghostSuffix, glueCode,
                cornerBracketCode, plasticCode, nonwovenFabricCode, polyethyleneCode,
                upper, additional, width, //plastic
                quantity, // cornerBracket
                oneFace,
                implementation
        ));
        return ResponseEntity.ok().body(list);
    }
}

