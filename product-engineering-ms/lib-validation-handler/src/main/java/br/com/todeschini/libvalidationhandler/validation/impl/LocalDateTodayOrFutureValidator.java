package br.com.todeschini.libvalidationhandler.validation.impl;

import br.com.todeschini.libvalidationhandler.validation.ValidationResult;
import br.com.todeschini.libvalidationhandler.validation.Validator;

import java.time.LocalDate;

public class LocalDateTodayOrFutureValidator implements Validator<LocalDate> {

    @Override
    public ValidationResult validate(LocalDate date) {
        ValidationResult validationResult = new ValidationResult(true);
        if (date != null && date.isBefore(LocalDate.now())) {
            validationResult = new ValidationResult(false, "Date must be today or in the future");
        }
        return validationResult;
    }
}
