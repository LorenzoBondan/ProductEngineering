package br.com.todeschini.libvalidationhandler.validation;

public record ValidationResult (boolean valid, String msg){

    public ValidationResult(boolean valid) {
        this(valid, null);
        if(!valid){
            throw new IllegalArgumentException("Should be true");
        }
    }

    public boolean invalid(){
        return !valid;
    }
}
