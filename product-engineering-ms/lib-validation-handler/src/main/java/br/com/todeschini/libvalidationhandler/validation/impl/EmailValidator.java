package br.com.todeschini.libvalidationhandler.validation.impl;

import br.com.todeschini.libvalidationhandler.validation.ValidationResult;
import br.com.todeschini.libvalidationhandler.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator implements Validator<String> {

    @Override
    public ValidationResult validate(String email) {
        ValidationResult validationResult = new ValidationResult(true);

        if (email != null && !isValidEmailFormat(email)) {
            validationResult = new ValidationResult(false, "Invalid email format");
        }

        return validationResult;
    }

    private boolean isValidEmailFormat(String email) {
        String emailRegex = "^(?!.*\\.{2})[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
}
