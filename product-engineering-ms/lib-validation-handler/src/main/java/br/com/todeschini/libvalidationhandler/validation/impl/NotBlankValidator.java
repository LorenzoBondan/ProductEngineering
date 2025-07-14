package br.com.todeschini.libvalidationhandler.validation.impl;

import br.com.todeschini.libvalidationhandler.validation.ValidationResult;
import br.com.todeschini.libvalidationhandler.validation.Validator;

public class NotBlankValidator implements Validator<String> {

    @Override
    public ValidationResult validate(String s) {
        ValidationResult validationResult = new ValidationResult(true);
        if (s != null && s.trim().isEmpty()) {
            validationResult = new ValidationResult(false, "Can't be empty.");
        }
        return validationResult;
    }
}
