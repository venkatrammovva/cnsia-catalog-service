package com.polarbookshop.catalogservice.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class BookValidationTests {
    private static Validator validator;

    @BeforeAll
    static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsCorrectThenValidationSucceeds() {
        var book = new Book("1234567890", "Title", "Author", 9.90);
        Set<ConstraintViolation<Book>> validations = validator.validate(book);
        assertThat(validations).isEmpty();
    }

    @Test
    void whenIsbnDefinedButIncorrectThenValidationFails() {
        var book = new Book("12345678901", "Title", "Author", 9.90);
        Set<ConstraintViolation<Book>> validations = validator.validate(book);
        assertThat(validations).hasSize(1);
        assertThat(validations.iterator().next().getMessage())
                .isEqualTo("The ISBN should be valid");
    }
}
