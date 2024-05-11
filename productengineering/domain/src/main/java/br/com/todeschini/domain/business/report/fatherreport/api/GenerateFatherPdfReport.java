package br.com.todeschini.domain.business.report.fatherreport.api;

import java.io.ByteArrayOutputStream;

public interface GenerateFatherPdfReport {

    ByteArrayOutputStream generatePdfReport(Long id);
}
