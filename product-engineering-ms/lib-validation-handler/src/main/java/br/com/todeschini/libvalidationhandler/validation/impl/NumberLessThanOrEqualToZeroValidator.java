package br.com.todeschini.libvalidationhandler.validation.impl;

import br.com.todeschini.libvalidationhandler.validation.ValidationResult;
import br.com.todeschini.libvalidationhandler.validation.Validator;

import java.math.BigDecimal;

public class NumberLessThanOrEqualToZeroValidator implements Validator<Number> {

    @Override
    public ValidationResult validate(Number s) {
        ValidationResult validationResult = new ValidationResult(true);
        if(s != null && new BigDecimal(s.toString()).compareTo(BigDecimal.ZERO) <= 0){
            validationResult = new ValidationResult(false, "Number must be less than or equal to zero");
        }
        return validationResult;
    }
}
