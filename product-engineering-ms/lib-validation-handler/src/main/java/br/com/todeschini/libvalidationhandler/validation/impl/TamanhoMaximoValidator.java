package br.com.todeschini.libvalidationhandler.validation.impl;

import br.com.todeschini.libvalidationhandler.validation.ValidationResult;
import br.com.todeschini.libvalidationhandler.validation.Validator;

public class TamanhoMaximoValidator implements Validator<String> {

    private final int maxLength;

    public TamanhoMaximoValidator(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public ValidationResult validate(String s) {
        String msg = null;

        if (s != null) {
            int length = s.length();
            msg = (length > maxLength)
                    ? String.format("Can't have more than %d characters.", maxLength)
                    : null;
        }
        return new ValidationResult(msg == null, msg);
    }
}

