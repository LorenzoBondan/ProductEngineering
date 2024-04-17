package br.com.todeschini.domain.exceptions;

import lombok.Getter;

@Getter
public class UniqueConstraintViolationException extends RuntimeException {

    private final String constraintName;
    private final String detailedMessage;

    public UniqueConstraintViolationException(String constraintName, String detailedMessage) {
        super("Violação de chave única: " + constraintName + ". " + detailedMessage);
        this.constraintName = constraintName;
        this.detailedMessage = detailedMessage;
    }
}
