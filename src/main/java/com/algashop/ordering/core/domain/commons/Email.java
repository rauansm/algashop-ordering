package com.algashop.ordering.core.domain.commons;

import com.algashop.ordering.core.domain.FieldValidations;

public record Email(String value) {

    public Email {
        FieldValidations.requiresValidEmail(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
