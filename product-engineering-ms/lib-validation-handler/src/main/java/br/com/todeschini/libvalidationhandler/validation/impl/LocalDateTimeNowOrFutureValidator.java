package br.com.todeschini.libvalidationhandler.validation.impl;

import br.com.todeschini.libvalidationhandler.validation.ValidationResult;
import br.com.todeschini.libvalidationhandler.validation.Validator;

import java.time.LocalDateTime;

public class LocalDateTimeNowOrFutureValidator implements Validator<LocalDateTime> {

    @Override
    public ValidationResult validate(LocalDateTime dateTime) {
        ValidationResult validationResult = new ValidationResult(true);
        if (dateTime != null && dateTime.isBefore(LocalDateTime.now())) {
            validationResult = new ValidationResult(false, "DateTime must be now or in the future");
        }
        return validationResult;
    }
}
