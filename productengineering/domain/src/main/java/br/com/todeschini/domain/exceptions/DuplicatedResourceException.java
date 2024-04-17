package br.com.todeschini.domain.exceptions;

import lombok.Getter;

import java.io.Serial;

@Getter
public class DuplicatedResourceException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    private static final String standardMessage = "Existem outros registros iguais a este.";
    private String detail;

    public DuplicatedResourceException() {
        super(standardMessage);
    }

    public DuplicatedResourceException(String detail){
        this();
        this.detail = detail;
    }
}
