package br.com.todeschini.domain.business.report.fatherreport.spi;

import br.com.todeschini.domain.business.publico.father.DFather;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public interface FatherReportMethods {

    ByteArrayOutputStream generatePdfReport(Long id);
}
