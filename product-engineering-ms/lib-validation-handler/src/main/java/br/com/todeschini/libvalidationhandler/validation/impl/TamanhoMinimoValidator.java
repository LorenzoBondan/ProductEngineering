package br.com.todeschini.libvalidationhandler.validation.impl;

import br.com.todeschini.libvalidationhandler.validation.ValidationResult;
import br.com.todeschini.libvalidationhandler.validation.Validator;

public class TamanhoMinimoValidator implements Validator<String> {

    private final int minLength;

    public TamanhoMinimoValidator(int minLength) {
        this.minLength = minLength;
    }

    @Override
    public ValidationResult validate(String s) {
        String msg = null;

        if (s != null) {
            int length = s.length();
            msg = (length < minLength)
                    ? String.format("Must have at least %d characters.", minLength)
                    : null;
        }
        return new ValidationResult(msg == null, msg);
    }

}

