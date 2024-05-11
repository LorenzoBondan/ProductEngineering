package br.com.todeschini.domain.business.report.fatherreport;

import br.com.todeschini.domain.business.report.fatherreport.api.FatherReportService;
import br.com.todeschini.domain.business.report.fatherreport.spi.FatherReportMethods;
import br.com.todeschini.domain.metadata.DomainService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@DomainService
public class FatherReportServiceImpl implements FatherReportService {

    private final FatherReportMethods fatherReportMethods;

    public FatherReportServiceImpl(FatherReportMethods fatherReportMethods) {
        this.fatherReportMethods = fatherReportMethods;
    }

    @Override
    public ByteArrayOutputStream generatePdfReport(Long id) {
        return fatherReportMethods.generatePdfReport(id);
    }
}
