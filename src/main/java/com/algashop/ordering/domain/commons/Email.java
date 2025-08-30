package com.algashop.ordering.domain.commons;

import com.algashop.ordering.domain.FieldValidations;

public record Email(String value) {

    public Email {
        FieldValidations.requiresValidEmail(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
