package br.com.todeschini.libvalidationhandler.validation.impl;

import br.com.todeschini.libvalidationhandler.validation.DataRange;
import br.com.todeschini.libvalidationhandler.validation.ValidationResult;
import br.com.todeschini.libvalidationhandler.validation.Validator;

import java.time.LocalDate;

public class StartDateBeforeFinalDateValidator implements Validator<DataRange> {

    @Override
    public ValidationResult validate(DataRange dataRange) {
        ValidationResult validationResult = new ValidationResult(true);

        if (dataRange != null) {
            LocalDate startDate = dataRange.getStartDate();
            LocalDate finalDate = dataRange.getFinalDate();

            if (startDate != null && finalDate != null && startDate.isAfter(finalDate)) {
                validationResult = new ValidationResult(false, "Start date must be before final date.");
            }
        }

        return validationResult;
    }
}
