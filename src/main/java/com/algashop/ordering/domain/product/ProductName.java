package com.algashop.ordering.domain.product;

import com.algashop.ordering.domain.FieldValidations;

public record ProductName(String value) {

    public ProductName {
        FieldValidations.requiresNonBlank(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
