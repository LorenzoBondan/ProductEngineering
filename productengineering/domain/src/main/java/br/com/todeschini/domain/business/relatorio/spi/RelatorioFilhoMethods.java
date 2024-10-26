package br.com.todeschini.domain.business.relatorio.spi;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public interface RelatorioFilhoMethods {

    ByteArrayOutputStream gerarRelatorioPdf(Integer id) throws IOException;
}
