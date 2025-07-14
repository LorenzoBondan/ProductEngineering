package br.com.todeschini.libvalidationhandler.validation.impl;

import br.com.todeschini.libvalidationhandler.validation.ValidationResult;
import br.com.todeschini.libvalidationhandler.validation.Validator;

import java.time.LocalDate;

public class LocalDateTodayOrPastValidator implements Validator<LocalDate> {

    @Override
    public ValidationResult validate(LocalDate date) {
        ValidationResult validationResult = new ValidationResult(true);
        if (date != null && date.isAfter(LocalDate.now())) {
            validationResult = new ValidationResult(false, "Date must be today or in the past");
        }
        return validationResult;
    }
}
