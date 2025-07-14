package br.com.todeschini.libexceptionhandler.exceptions;

import lombok.Getter;

@Getter
public class UniqueConstraintViolationException extends RuntimeException {

    public UniqueConstraintViolationException(String msg){
        super(msg);
    }
}
