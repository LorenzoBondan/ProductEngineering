package br.com.todeschini.libtestcommonsdomain.base;

import br.com.todeschini.libexceptionhandler.exceptions.ValidationException;
import br.com.todeschini.libvalidationhandler.validation.NamedValidator;
import br.com.todeschini.libvalidationhandler.validation.ValidationBuilder;
import br.com.todeschini.libvalidationhandler.validation.Validator;
import org.junit.jupiter.api.function.Executable;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class BaseDomainValidatorTest {

    /**
     * Validates a single field of a domain object with a specific Validator.
     * Throws if the validation fails.
     *
     * @param domainObject the object to test
     * @param fieldName the field name to validate
     * @param validator the validator to apply to the field
     */
    protected <T> void assertFieldValidationFails(Object domainObject, String fieldName, Validator<T> validator) {
        try {
            Field field = domainObject.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            @SuppressWarnings("unchecked")
            T fieldValue = (T) field.get(domainObject);

            NamedValidator<T> namedValidator = new NamedValidator<>(fieldName, validator);
            ValidationBuilder validationBuilder = new ValidationBuilder().add(namedValidator, fieldValue);

            Executable validationExecutable = () -> validationBuilder.validate();

            assertThrows(ValidationException.class, validationExecutable,
                    () -> "Expected validation to fail for field: " + fieldName);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to access field " + fieldName, e);
        }
    }
}
