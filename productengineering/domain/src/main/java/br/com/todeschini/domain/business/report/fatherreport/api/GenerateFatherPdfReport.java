package br.com.todeschini.domain.business.report.fatherreport.api;

import br.com.todeschini.domain.business.publico.father.DFather;

import java.io.ByteArrayOutputStream;

public interface GenerateFatherPdfReport {

    ByteArrayOutputStream generatePdfReport(Long id) throws Exception;
}
