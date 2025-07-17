package br.com.todeschini.libvalidationhandler.validation.impl;

import br.com.todeschini.libvalidationhandler.validation.ValidationResult;
import br.com.todeschini.libvalidationhandler.validation.Validator;

public class ObjetoNaoNuloValidator implements Validator<Object> {
    @Override
    public ValidationResult validate(Object o) {
        ValidationResult result = new ValidationResult(true);
        if(o == null){
            result = new ValidationResult(false, "Can't be null");
        }
        return result;
    }
}
