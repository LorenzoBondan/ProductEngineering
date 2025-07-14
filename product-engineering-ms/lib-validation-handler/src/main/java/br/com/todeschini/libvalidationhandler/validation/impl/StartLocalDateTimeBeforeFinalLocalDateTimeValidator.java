package br.com.todeschini.libvalidationhandler.validation.impl;

import br.com.todeschini.libvalidationhandler.validation.LocalDateTimeRange;
import br.com.todeschini.libvalidationhandler.validation.ValidationResult;
import br.com.todeschini.libvalidationhandler.validation.Validator;

import java.time.LocalDateTime;

public class StartLocalDateTimeBeforeFinalLocalDateTimeValidator implements Validator<LocalDateTimeRange> {

    @Override
    public ValidationResult validate(LocalDateTimeRange range) {
        if (range == null) {
            return new ValidationResult(true);
        }

        LocalDateTime start = range.getStart();
        LocalDateTime end = range.getEnd();

        if (start == null || end == null) {
            return new ValidationResult(true);
        }

        if (start.isAfter(end)) {
            return new ValidationResult(false, "Start LocalDateTime must be before final LocalDateTime.");
        }

        return new ValidationResult(true);
    }
}
