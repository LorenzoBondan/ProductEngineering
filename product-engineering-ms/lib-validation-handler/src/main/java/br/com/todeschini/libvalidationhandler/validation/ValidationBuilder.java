package br.com.todeschini.libvalidationhandler.validation;

import br.com.todeschini.libexceptionhandler.exceptions.ValidationException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class ValidationBuilder {

    private final List<ValidationCommand<?>> validatorList;

    public ValidationBuilder() {
        validatorList = new ArrayList<>();
    }

    public <T> ValidationBuilder add(NamedValidator<T> namedValidator, T value){
        this.validatorList.add(new ValidationCommand<T>(namedValidator, value));
        return this;
    }

    public void validate(){
        ValidationResult vr = validatorList.stream()
                .filter(Objects::nonNull)
                .map(ValidationCommand::evaluate)
                .filter(ValidationResult::invalid)
                .peek(e -> log.error("Validation error: {}", e.msg()))
                .findFirst()
                .orElse(null);
        if(vr != null){
            throw new ValidationException(vr.msg());
        }
    }
}
