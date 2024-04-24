package br.com.todeschini.domain.publico;

import br.com.todeschini.domain.business.publico.color.DColor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidationTests {

    public static void main(String[] args) {
        DColor color = DColor.builder()
                .code(123L)
                .name("ABC1D23")
                .build();
        color.validate();

        log.info("Sucesso");
    }
}
