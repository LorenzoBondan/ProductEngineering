package br.com.todeschini.webapi.api.v1.rest.report;

import br.com.todeschini.domain.business.publico.son.DSon;
import br.com.todeschini.domain.business.publico.son.api.SonService;
import br.com.todeschini.domain.business.report.fatherreport.api.FatherReportService;
import br.com.todeschini.persistence.entities.enums.Status;
import br.com.todeschini.persistence.publico.son.SonRepository;
import br.com.todeschini.webapi.api.v1.rest.publico.son.projection.SonDTO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Father Report")
@RestController
@RequestMapping(value = "/fatherReport")
public class FatherReportController {

    private final FatherReportService service;

    public FatherReportController(FatherReportService service) {
        this.service = service;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully search"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"), // when not logged
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ANALYST', 'ROLE_OPERATOR')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<byte[]> generatePdfReport(@PathVariable("id") Long id) {
        try {
            ByteArrayOutputStream pdfStream = service.generatePdfReport(id);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "report.pdf");
            headers.setContentLength(pdfStream.size());

            return new ResponseEntity<>(pdfStream.toByteArray(), headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
