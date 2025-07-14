package br.com.todeschini.libvalidationhandler.validation.impl;

import br.com.todeschini.libvalidationhandler.validation.ValidationResult;
import br.com.todeschini.libvalidationhandler.validation.Validator;

import java.time.LocalDateTime;

public class LocalDateTimeNowOrPastValidator implements Validator<LocalDateTime> {

    @Override
    public ValidationResult validate(LocalDateTime dateTime) {
        ValidationResult validationResult = new ValidationResult(true);
        if (dateTime != null && dateTime.isAfter(LocalDateTime.now())) {
            validationResult = new ValidationResult(false, "DateTime must be now or in the past");
        }
        return validationResult;
    }
}
